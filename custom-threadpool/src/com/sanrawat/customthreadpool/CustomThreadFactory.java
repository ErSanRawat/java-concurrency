package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * CUSTOM THREAD FACTORY DEMO
 * ---------------------------
 * - Helps create threads with custom names, priorities, daemon.
 * - Very common in large production applications for debugging.
 */

public class CustomThreadFactory{

    public static void main(String[] args) {

        ThreadFactory factory = runnable -> {
            Thread t = new Thread(runnable);
            t.setName("custom-worker-" + t.threadId());
            t.setDaemon(false);
            return t;
        };

        try (ExecutorService service = Executors.newFixedThreadPool(3, factory)) {

            for (int i = 1; i <= 5; i++) {
                service.submit(() -> System.out.println(Thread.currentThread().getName()));
            }

            service.shutdown();
        }
    }
}
