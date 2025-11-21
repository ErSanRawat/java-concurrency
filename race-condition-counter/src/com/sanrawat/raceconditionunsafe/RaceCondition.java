package com.sanrawat.raceconditionunsafe;

public class RaceCondition {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        int THREADS = 5;
        int INCREMENTS = 10_000;

        Thread[] threads = new Thread[THREADS];

        // Create worker threads
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    counter.increment();
                }
            }, "worker-" + i);
        }

        // Start all threads
        for (Thread t : threads) {
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        long expected = (long) THREADS * INCREMENTS;

        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + counter.getValue());
        System.out.println("Race Occurred? " + (counter.getValue() != expected));
    }
}