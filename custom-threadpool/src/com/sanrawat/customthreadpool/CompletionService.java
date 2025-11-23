package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * COMPLETION SERVICE DEMO
 * ------------------------
 * - When multiple tasks run asynchronously,
 *   CompletionService returns whichever finishes FIRST.
 * - Useful for scrapers, API multi-calls, search queries.
 */

public class CompletionService {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Object> service = new ExecutorCompletionService<>(executor);

        service.submit(() -> { Thread.sleep(3000); return "Task 3 done"; });
        service.submit(() -> { Thread.sleep(1000); return "Task 1 done"; });
        service.submit(() -> { Thread.sleep(2000); return "Task 2 done"; });

        for (int i = 0; i < 3; i++) {
            System.out.println(service.take().get());
        }

        executor.shutdown();
    }
}
