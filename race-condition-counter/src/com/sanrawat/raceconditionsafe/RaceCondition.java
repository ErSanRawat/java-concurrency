package com.sanrawat.raceconditionsafe;

public class RaceCondition {

    public static void main(String[] args) throws InterruptedException {

        CounterSynchronized counterSynchronized=new CounterSynchronized();
        CounterSafeAtomic counterSafeAtomic=new CounterSafeAtomic();
        CounterSafeLock counterSafeLock=new CounterSafeLock();

        int THREADS = 5;
        int INCREMENTS = 10_000;

        Thread[] threads = new Thread[THREADS];

        // Create worker threads
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    counterSynchronized.increment();
                    counterSafeAtomic.increment();
                    counterSafeLock.increment();
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
        System.out.println(" CounterSynchronized Result : ");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + counterSynchronized.getValue());
        System.out.println("Race Occurred? " + (counterSynchronized.getValue() != expected));
        System.out.println(" CounterAtomic Result : ");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + counterSafeAtomic.getCount());
        System.out.println("Race Occurred? " + (counterSafeAtomic.getCount() != expected));
        System.out.println(" CounterSafeLock Result : ");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + counterSafeLock.getCount());
        System.out.println("Race Occurred? " + (counterSafeLock.getCount() != expected));
    }
}