package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * PRODUCER-CONSUMER USING BLOCKINGQUEUE
 * --------------------------------------
 * This is the CLEANEST way to build Producer-Consumer in Java.
 *
 * Why BlockingQueue?
 * - Handles synchronization internally
 * - No need for wait/notify
 * - put() blocks if queue is full → natural backpressure
 * - take() blocks if queue is empty → no busy waiting
 *
 * Real-world usage:
 * - Data ingestion pipelines
 * - Event processing systems
 * - Log collection systems
 */

public class ProducerConsumerUsingBlockingQueue {

    public static void main(String[] args) {

        // Queue with a capacity of 5 elements
        // This automatically controls flow
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer — continuously generates numbers
        Runnable producer = () -> {
            int value = 0;

            while (true) {
                try {
                    System.out.println("Producing → " + value);

                    // put() blocks if queue is full
                    queue.put(value);

                    value++;
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {}
            }
        };

        // Consumer — continuously consumes numbers
        Runnable consumer = () -> {
            while (true) {
                try {
                    // take() waits if queue is empty (no spinning)
                    int item = queue.take();
                    System.out.println("Consuming ← " + item);

                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        };

        // Start producer & consumer threads
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
