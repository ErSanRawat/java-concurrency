package com.sanrawat.threadrunnablecallablefuture;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/*
//=======================================================
// File: FetchURLRunnable.java
// Basic Version: Using Thread + Runnable
// ------------------------------------------------------
// This version demonstrates how to launch
// multiple threads manually without Executors.
// Good for learning, not recommended for production.
// ======================================================
// It helps you understand how to create multiple threads manually, but it is not recommended for real-world applications because:
//      Hard to manage many threads
//      No result return from Runnable
//      No thread reuse → expensive
//      No timeout or error control
This version uses simple Thread objects and Runnable tasks to fetch URLs concurrently.
 */
public class FetchURLRunnable {

    public static void main(String[] args) throws Exception {

        // List of URLs to fetch concurrently
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


        // Launch one thread per URL
        for (String url : urls) {
            Thread thread = new Thread(() -> fetch(url));
            thread.start();
        }
    }

    private static void fetch(String url) {
        try {
            // BufferedReader reads from the URL input stream
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            System.out.println(Thread.currentThread().getName() + " → Fetched: " + url);
            br.close();
        } catch (Exception e) {
            System.out.println("Error fetching " + url + ": " + e.getMessage());
        }
    }
}
