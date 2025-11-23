package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * FUTURE + CALLABLE DEMO
 * -----------------------
 * - Callable is like Runnable BUT it RETURNS a value and THROWS exceptions.
 * - Future represents a pending result.
 *
 * Use cases:
 * - Async HTTP calls
 * - Database queries
 * - CPU-heavy calculations
 * - Parallel execution of independent tasks
 */
public class FutureCallableUsage {
    public static void main(String[] args) throws Exception {

        // Create pool with 2 worker threads
        ExecutorService service = Executors.newFixedThreadPool(2);

        // This task returns a number after 1 sec
        Callable<Integer> task = () -> {
            System.out.println("Computing in: " + Thread.currentThread().getName());
            Thread.sleep(1000);

            return 42; // result returned from worker thread
        };

        // submit() returns Future — not the actual result
        Future<Integer> future = service.submit(task);

        System.out.println("Main thread is free to do other work...");

        // future.get() blocks until result is ready
        Integer result = future.get();

        System.out.println("Result received = " + result);

        service.shutdown();
    }
}
