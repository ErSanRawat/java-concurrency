package com.sanrawat.customthreadpool;
/**
 * CACHED THREAD POOL DEMO
 * ------------------------
 * - Creates threads dynamically based on workload.
 * - Reuses idle threads.
 * - Suitable for short-lived async tasks.
 * - Not recommended for CPU-heavy tasks (can explode thread count).
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args) {

        // Cached Thread Pool:
        // - Creates unlimited threads if required
        // - Reuses idle threads after 60 seconds
        ExecutorService pool = Executors.newCachedThreadPool();

        // Submitting many lightweight tasks
        for (int i = 1; i <= 20; i++) {
            int id = i;

            pool.submit(() -> {
                // Threads are created dynamically as needed
                System.out.println("Task " + id + " → Executed by: "
                        + Thread.currentThread().getName());
            });
        }

        // Shuts down after tasks finish
        pool.shutdown();
    }
}
