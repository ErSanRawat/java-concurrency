package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * REJECTED EXECUTION HANDLER DEMO
 * --------------------------------
 * - Triggered when executor can't accept more tasks.

 * Why tasks get rejected?
 * - Queue is FULL
 * - Maximum pool size reached
 * - Executor is shutting down

 * Used in production:
 * - Logging rejected tasks
 * - Retrying later
 * - Sending alerts (Prometheus, Grafana)
 */

public class RejectedExecutionHandlerUsage {

    public static void main(String[] args) {

        // Handler to execute when task is rejected
        RejectedExecutionHandler handler = (r, executor) -> {
            System.out.println("❌ Task rejected: " + r.toString());
            System.out.println("Reason: Queue full OR executor shutting down.");
        };

        // ThreadPoolExecutor with:
        // corePoolSize = 1  → 1 worker thread
        // maxPoolSize  = 1  → cannot create more than 1 thread
        // queueCapacity = 1
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                handler // custom rejection handler
        );

        // 1st task → running
        executor.submit(() -> {
            try { Thread.sleep(3000); } catch (Exception ignored) {}
        });

        // 2nd task → waiting in queue
        executor.submit(() -> System.out.println("Task 2 executing"));

        // 3rd task → REJECTED (queue full + 1 running task)
        executor.submit(() -> System.out.println("Task 3 executing"));

        executor.shutdown();
    }
}
