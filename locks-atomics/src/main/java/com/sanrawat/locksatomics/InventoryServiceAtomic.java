package main.java.com.sanrawat.locksatomics;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HIGH-PERFORMANCE ATOMIC INVENTORY COUNTER
 * ------------------------------------------
 * Concept:
 *  AtomicInteger provides lock-free, thread-safe operations.
 *
 * Why AtomicInteger?
 *  - Very high performance under heavy concurrency.
 *  - Non-blocking (no locking).
 *  - Ideal for counters, metrics, rate-limiting, likes/views, stock pre-checks.
 *
 * When NOT to use?
 *  - When business logic requires a multi-step atomic operation.
 *    In those cases, use synchronized or ReentrantLock.
 */


public class InventoryServiceAtomic {

    // Atomic integer for lock-free stock updates
    private final AtomicInteger stock = new AtomicInteger(10);

    public void purchase(String threadName) {
        // Atomically check & decrement using CAS (compare-and-set)

        while (true) {
            int currentStock = stock.get();         // read current stock

            if (currentStock == 0) {
                System.out.println(threadName + " FAILED — Stock empty.");
                return; // stop trying
            }

            // CAS: try to set stock from currentStock → currentStock - 1
            boolean updated = stock.compareAndSet(currentStock, currentStock - 1);

            if (updated) {
                // success → no other thread modified stock in-between
                System.out.println(threadName + " purchased 1 item.");
                return;
            }

            // if CAS failed → retry because another thread changed stock
        }
    }

    public int getStock() {
        return stock.get();
    }
}
