package com.sanrawat.customthreadpool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SINGLE THREADED EXECUTOR DEMO
 * ------------------------------
 * - Executes tasks one by one (FIFO order).
 * - Guaranteed task ordering.
 * - Useful for logging, saving audit records, file operations.
 */

public class SingleThreadExecutor {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 5; i++) {
            int id = i;
            executor.submit(() ->
                    System.out.println("Executing: " + id + " in " + Thread.currentThread().getName())
            );
        }

        executor.shutdown();
    }
}
