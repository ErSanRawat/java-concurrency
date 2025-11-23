package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * REJECTED EXECUTION HANDLER DEMO
 * --------------------------------
 * - Happens when queue is full and executor cannot accept new tasks.
 * - Custom handler lets you log, retry, or save for later.
 */


public class RejectedExecutionHandlerUsage {

    public static void main(String[] args) {
        RejectedExecutionHandler handler = (r, executor) ->
                System.out.println("Task rejected: " + r.toString());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                handler
        );

        executor.submit(() -> {
            try { Thread.sleep(3000); } catch (Exception ignored) {}
        });

        executor.submit(() -> System.out.println("Task2")); // queued
        executor.submit(() -> System.out.println("Task3")); // REJECTED

        executor.shutdown();
    }
}
