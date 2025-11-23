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
            ExecutorService pool = Executors.newFixedThreadPool(3);

            for (int i = 1; i <= 10; i++) {
                final int taskId = i;

                pool.submit(() -> {
                    System.out.println("Executing Task " + taskId +
                            " in Thread: " + Thread.currentThread().getName());
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                });
            }

            pool.shutdown();
        }


}
