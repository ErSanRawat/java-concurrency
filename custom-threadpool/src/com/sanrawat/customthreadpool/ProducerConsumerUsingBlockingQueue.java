package com.sanrawat.customthreadpool;
import java.util.concurrent.*;

/**
 * PRODUCER-CONSUMER USING BLOCKINGQUEUE
 * --------------------------------------
 * - Demonstrates thread communication without wait/notify.
 * - BlockingQueue handles synchronization.
 * - Used in pipeline systems (ex: ingestion → processing → storing).
 */

public class ProducerConsumerUsingBlockingQueue {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        Runnable producer = () -> {
            int value = 0;
            while (true) {
                try {
                    System.out.println("Producing: " + value);
                    queue.put(value++);
                } catch (InterruptedException ignored) {}
            }
        };

        Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println("Consuming: " + queue.take());
                } catch (InterruptedException ignored) {}
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
