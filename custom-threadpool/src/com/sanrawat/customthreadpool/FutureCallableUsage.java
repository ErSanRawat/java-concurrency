package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * FUTURE + CALLABLE DEMO
 * -----------------------
 * - Submits tasks that return values.
 * - Future.get() blocks until result available.
 * - Used for network calls, DB queries, calculation tasks.
 */
public class FutureCallableUsage {
    public static void main(String[] args) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {
            Thread.sleep(1000);
            return 42; // result
        };

        Future<Integer> result = service.submit(task);
        System.out.println("Waiting for result...");
        System.out.println("Result = " + result.get());

        service.shutdown();
    }
}
