package com.sanrawat.customthreadpool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FIXED THREAD POOL DEMO
 * -----------------------
 * This class shows how a FixedThreadPool works.
 * - ExecutorService creates N fixed worker threads.
 * - Tasks beyond the limit go into a queue.
 * - Used in production when you want *bounded concurrency*
 *   (ex: 10 threads hitting DB, 5 threads calling APIs, etc).
 */
public class FixedThreadPool {
    public static void main(String[] args) {

        // Create a thread pool with exactly 3 threads.
        // Only 3 tasks run concurrently at any moment.
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // Submitting 10 independent tasks to the pool
        for (int i = 1; i <= 10; i++) {
            final int taskId = i; // Must be final or effectively final inside lambda

            pool.submit(() -> {
                // Shows which thread processed which task
                System.out.println("Executing Task " + taskId +
                        " in Thread: " + Thread.currentThread().getName());

                // Simulate work (I/O, DB call, heavy load)
                try {
                    Thread.sleep(1000);  // Simulates a real workload
                } catch (InterruptedException ignored) {}
            });
        }

        // IMPORTANT:
        // shutdown() means:
        // - No new tasks accepted
        // - Existing tasks continue and pool exits AFTER finishing them
        pool.shutdown();
    }


}
