package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * COMPLETION SERVICE DEMO
 * ------------------------
 * Why CompletionService?
 * - When submitting MULTIPLE asynchronous tasks,
 *   Future only gives you results in submission order.
 * - CompletionService gives results in COMPLETION ORDER.
 *   (fastest task finishes first → retrieved first)
 * Perfect for:
 * - Web scrapers hitting 10 sites
 * - Microservice multi-calls (e.g., search results)
 * - Machine learning inference batches
 */

public class CompletionService {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Wrap executor inside completion service
        ExecutorCompletionService<Object> service = new ExecutorCompletionService<>(executor);

        // Submit 3 tasks taking different time
        service.submit(() -> { Thread.sleep(3000); return "Task 3 done"; });
        service.submit(() -> { Thread.sleep(1000); return "Task 1 done"; });
        service.submit(() -> { Thread.sleep(2000); return "Task 2 done"; });

        // Now read results AS THEY FINISH!
        for (int i = 0; i < 3; i++) {
            var result = service.take().get(); // blocks until next completed
            System.out.println(result);
        }

        executor.shutdown();
    }
}
