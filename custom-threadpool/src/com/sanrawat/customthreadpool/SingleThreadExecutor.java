package com.sanrawat.customthreadpool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SINGLE THREADED EXECUTOR DEMO
 * ------------------------------
 * - Executes tasks one by one (FIFO order).
 * - Guaranteed task ordering.
 * - Useful for logging, saving audit records, file operations.
 * - Useful when:
 *       * You need strict ordering (logs, sequential file updates)
 *       * A shared resource must be accessed by one thread only
 */

public class SingleThreadExecutor {
    public static void main(String[] args) {

        // Creates an executor that uses ONE worker thread.
        // Guarantees: submit() tasks NEVER overlap simultaneously.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit 5 ordered tasks
        for (int i = 1; i <= 5; i++) {
            int id = i;

            executor.submit(() -> {
                // Demonstrate that only one thread name appears
                System.out.println("Running Task " + id +
                        " on " + Thread.currentThread().getName());

                // Simulate work
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            });
        }

        // Shutdown executor after tasks are completed
        executor.shutdown();
    }
}
