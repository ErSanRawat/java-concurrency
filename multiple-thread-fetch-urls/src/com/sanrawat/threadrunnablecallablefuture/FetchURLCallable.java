package com.sanrawat.threadrunnablecallablefuture;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
   ExecutorService Version — Using Callable & Future
   (Most Recommended — scalable and clean)
// =====================================================
// File: FetchURLCallable.java
// Recommended Version: ExecutorService + Callable
// -----------------------------------------------------
// This approach is scalable, clean, and used in production.
// - ExecutorService manages a thread pool
// - Callable allows returning results
// - Future collects output from each task
// =====================================================
   This is the industry standard for running tasks concurrently because:
           Threads are reused from a pool → high performance
           You can get return values from tasks
           You can submit hundreds or thousands of tasks easily
           Clean, scalable, maintainable
   This version is ideal for normal production usage and is the best for interviews.
*
* */

public class FetchURLCallable {

    public static void main(String[] args) throws Exception {

        String[] urls = {
                "https://example.com",
                "https://google.com",
                "https://github.com",
                "https://stackoverflow.com",
                "https://openai.com",
                "https://oracle.com",
                "https://microsoft.com",
                "https://amazon.com",
                "https://wikipedia.org",
                "https://bbc.com"
        };

        // Create a thread pool with 10 threads
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            List<Future<String>> futures = new ArrayList<>();

            for (String url : urls) {
                // Callable returns a String result
                Callable<String> task = () -> fetch(url);
                // Submit task to ExecutorService
                futures.add(executor.submit(task));
            }

            // Print all results from Future
            for (Future<String> f : futures) {
                System.out.println(f.get());// get() waits for the result
            }

            executor.shutdown();
        }
    }

    private static String fetch(String url) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {

            return Thread.currentThread().getName() + " → Successfully fetched: " + url;

        } catch (Exception e) {
            return Thread.currentThread().getName() + " → Failed: " + url + " | " + e.getMessage();
        }
    }
}
