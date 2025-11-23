package com.sanrawat.customthreadpool;
import java.util.concurrent.*;
/**
 * GRACEFUL SHUTDOWN DEMO
 * -----------------------
 * - Ensures all tasks complete before app exits.
 * - Used in production servers for graceful stop.
 */

public class GracefulShutdown{

    public static void main(String[] args) throws InterruptedException {

        try (ExecutorService service = Executors.newFixedThreadPool(2)) {

            for (int i = 1; i <= 5; i++) {
                service.submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println("Completed by " + Thread.currentThread().getName());
                });
            }

            service.shutdown(); // no more tasks accepted

            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        }
    }
}
