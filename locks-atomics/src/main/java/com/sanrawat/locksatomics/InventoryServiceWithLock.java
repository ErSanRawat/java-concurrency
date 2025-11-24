package main.java.com.sanrawat.locksatomics;
import java.util.concurrent.locks.ReentrantLock;

/**
 * INVENTORY SERVICE USING REENTRANTLOCK
 * --------------------------------------
 * Concept:
 *  This class simulates reducing product stock in a high-concurrency environment.
 *  Multiple threads (orders) attempt to purchase items at the same time.

 * Why ReentrantLock?
 *  - Gives finer control than synchronized.
 *  - Allows timed lock attempts (tryLock), interruptible locks, fairness policies.
 *  - Useful in production systems like payment processing, stock reservation,
 *    ticket booking, order placement, etc.

 * When to use?
 *  - When you need more control than synchronized.
 *  - When operations may need timeouts, manual locking/unlocking, or fairness.
 */

public class InventoryServiceWithLock {    // shared product stock
    private int stock = 10;

    // ReentrantLock used for explicit locking
    private final ReentrantLock lock = new ReentrantLock(true); // fairness = true

    /**
     * Reduce stock safely using lock.
     */
    public void purchase(String threadName) {
        lock.lock();  // manual lock acquired
        try {
            // critical section — only one thread at a time

            if (stock > 0) {
                System.out.println(threadName + " purchased 1 item.");
                stock--;  // update shared state safely
            } else {
                System.out.println(threadName + " FAILED — Stock empty.");
            }

        } finally {
            lock.unlock(); // MUST unlock inside finally block to avoid deadlocks
        }
    }

    public int getStock() {
        return stock;
    }
}
