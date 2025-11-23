package com.sanrawat.threadrunnablecallablefuture;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.*;

// ===========================================================================
// File: FetchURLWithTimeout.java
// Advanced Version: Timeout + Cancellation + Error Handling
// ---------------------------------------------------------------------------
// This version reflects real-world usage where external URLs may hang.
// - Timeout prevents long waits
// - Future.cancel() stops slow tasks
// - Handles exceptions gracefully
// =========================================================================
public class FetchURLWithTimeout {

    public static void main(String[] args)  {

        String[] urls = {
                "https://example.com",
                "https://google.com",
                "https://github.com",
                "https://stackoverflow.com",
                "https://openai.com",
                "https://oracle.com",
                "https://microsoft.com",
                "https://amazon.com",
                "https://wikipedia.org",
                "https://bbc.com"
        };

        // Thread pool with 10 threads
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {

            for (String url : urls) {
                Future<String> future = executor.submit(() -> fetch(url));

                try {
                    // Wait max 3 seconds for URL fetch
                    String result = future.get(5, TimeUnit.SECONDS);
                    System.out.println(result);

                } catch (TimeoutException e) {
                    // Stop slow task
                    future.cancel(true);
                    System.out.println("Timeout fetching: " + url);

                } catch (Exception e) {
                    System.out.println("Error fetching: " + url + " → " + e.getMessage());
                }
            }

            executor.shutdown();
        }
    }

    private static String fetch(String url) throws Exception {
        /*
        *
          BufferedReader reads data efficiently in chunks
          Better for network I/O
          Prevents slow reading byte-by-byte
          Makes your code faster & safer when reading from URLs

          * Version 3 – ExecutorService + Timeout + Error Handling (Production-Grade)
              This is an advanced and more robust version for real-world systems.
            It adds:
                   Timeouts (stop slow websites or hangs)
                   Exception handling
                   Task cancellation
                   Graceful thread pool shutdown
             Useful when fetching URLs from unreliable or slow external servers.
             This version best represents how enterprise applications handle remote calls safely

          * */
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            return Thread.currentThread().getName() + " → Successfully fetched: " + url;
        }
    }
}
