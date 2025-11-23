package com.sanrawat.completablefuture;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

    /**
     * CompletableFuturePipeline

     * This class demonstrates how to build an asynchronous pipeline using CompletableFuture.

     * Concepts Used:
     * 1. CompletableFuture.supplyAsync()
     *      - Used to start asynchronous tasks that produce a result (fetching data).

     * 2. thenApply()
     *      - Used to transform the result (processing data).

     * 3. thenCompose()
     *      - Used to chain async tasks that return another CompletableFuture
     *        (best for sequential async operations).

     * 4. thenAccept()
     *      - Used for final operations that do not return any result (storing data).

     * 5. Custom Thread Pool
     *      - Using ExecutorService instead of the default ForkJoinPool for
     *        better control in production environments.

     * When to Use This Pattern:
     * -------------------------
     * ✔ When you have multiple asynchronous operations that depend on each other's result
     * ✔ When you want non-blocking I/O like calling external APIs or DB operations
     * ✔ When you want a clean, readable async pipeline instead of nested callbacks
     * ✔ When you need parallelism without manually managing threads

     * Typical Use Cases:
     * ------------------
     * - Fetching data from a remote API → processing → saving to DB
     * - Reading a file → cleaning/transforming → uploading
     * - Calling microservices sequentially
     *
     */
    public class CompletableFuturePipelineUsage {

        // Custom thread pool for production-grade async execution
        private static final ExecutorService executor = Executors.newFixedThreadPool(5);

        public static void main(String[] args) {

            // Step 1: Fetch data asynchronously
            CompletableFuture<String> fetchFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println("Fetching user data from remote API...");
                sleep(1000);
                return "RawUserData{userId=101, name='Sandeep'}";
            }, executor);

            // Step 2: Process the fetched data
            CompletableFuture<String> processedFuture = fetchFuture.thenApply(rawData -> {
                System.out.println("Processing data...");
                sleep(800);

                // Example of transformation
                return rawData.replace("RawUserData", "ProcessedUser");
            });

            // Step 3: Store the processed data asynchronously
            CompletableFuture<Void> storeFuture = processedFuture.thenAcceptAsync(processedData -> {
                System.out.println("Storing processed data into database...");
                sleep(700);

                System.out.println("Stored successfully: " + processedData);
            }, executor);

            // Step 4: Combine all and wait for completion
            storeFuture.join();
            executor.shutdown();

            System.out.println("Pipeline Completed Successfully!");
        }

        /**
         * Utility sleep method so code looks cleaner.
         */
        private static void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

}
