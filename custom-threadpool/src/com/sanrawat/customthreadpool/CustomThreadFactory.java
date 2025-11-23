package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * CUSTOM THREAD FACTORY DEMO
 * ---------------------------
 * Why ThreadFactory?
 * - Allows creating threads with:
 *      * Custom Names (VERY useful for debugging)
 *      * Custom Priority
 *      * Daemon/Non-daemon behavior
 *
 * Used in production for:
 * - Identifying thread groups (api-worker-1, db-worker-2, etc)
 * - Monitoring CPU usage by specific thread groups
 * - Debugging deadlocks by naming threads
 */
public class CustomThreadFactory{

    public static void main(String[] args) {

        // Custom ThreadFactory implementation using lambda
        ThreadFactory factory = runnable -> {
            Thread t = new Thread(runnable);

            // Assign a meaningful name (critical in microservices)
            t.setName("custom-worker-" + t.getId());

            // By default, threads are non-daemon → safe for production
            t.setDaemon(false);

            return t;
        };

        // Create pool of 3 threads using the custom thread factory
        try (ExecutorService service = Executors.newFixedThreadPool(3, factory)) {

            // Submit some tasks
            for (int i = 1; i <= 5; i++) {

                service.submit(() -> {
                    System.out.println("Executing on → " + Thread.currentThread().getName());

                    // Simulate small task
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ignored) {
                    }
                });
            }

            service.shutdown();
        }
    }

}
