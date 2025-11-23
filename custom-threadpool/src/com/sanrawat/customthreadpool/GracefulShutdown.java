package com.sanrawat.customthreadpool;
import java.util.concurrent.*;
/**
 * GRACEFUL SHUTDOWN DEMO
 * -----------------------
 * - Demonstrates how to gracefully stop an ExecutorService.
 * - This is CRITICAL in real applications (Spring Boot / Microservices).

 * shutdown():
 *      - No new tasks accepted
 *      - Existing tasks complete normally

 * awaitTermination():
 *      - Waits for running tasks to finish

 * shutdownNow():
 *      - Attempts to interrupt all running tasks immediately
 *      - Used ONLY when app must stop right now (ex: service crash)
 */
public class GracefulShutdown{

    public static void main(String[] args) throws InterruptedException {

        try (ExecutorService service = Executors.newFixedThreadPool(2)) {

            // Submit tasks that each take 1 second
            for (int i = 1; i <= 5; i++) {
                service.submit(() -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Completed by: " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        System.out.println("Task interrupted!");
                    }
                });
            }

            // Step 1: Initiate graceful shutdown
            System.out.println("Shutting down executor (gracefully)...");
            service.shutdown();

            // Step 2: Wait for tasks to finish
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {

                System.out.println("Tasks didn't finish in time. Forcing shutdown...");

                // Step 3: Force shutdown (optional fallback)
                service.shutdownNow();
            }
        }

        System.out.println("Executor fully stopped.");
    }
}
