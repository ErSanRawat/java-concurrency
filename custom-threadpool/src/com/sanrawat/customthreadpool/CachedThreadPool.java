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
        try (ExecutorService pool = Executors.newCachedThreadPool()) {

            for (int i = 1; i <= 20; i++) {
                int id = i;
                pool.submit(() -> {
                    System.out.println("Task " + id + " → " + Thread.currentThread().getName());
                });
            }

            pool.shutdown();
        }
    }
}
