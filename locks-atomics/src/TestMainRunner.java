import main.java.com.sanrawat.locksatomics.InventoryServiceAtomic;
import main.java.com.sanrawat.locksatomics.InventoryServiceWithLock;

/**
 * Test Runner to simulate concurrent inventory purchase requests.
 * ---------------------------------------------------------------
 * What it demonstrates:
 *  - Multiple threads performing purchase operations simultaneously.
 *  - Shows difference between ReentrantLock and AtomicInteger approaches.
 */

public class TestMainRunner {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("==== Testing Inventory with ReentrantLock ====");
        InventoryServiceWithLock inventoryLock = new InventoryServiceWithLock();

        runSimulation(inventoryLock::purchase);

        System.out.println("\nFinal Stock (ReentrantLock): " + inventoryLock.getStock());

        System.out.println("\n==== Testing Inventory with AtomicInteger ====");
        InventoryServiceAtomic inventoryAtomic = new InventoryServiceAtomic();

        runSimulation(inventoryAtomic::purchase);

        System.out.println("\nFinal Stock (AtomicInteger): " + inventoryAtomic.getStock());
    }

    // Method reference used for common thread simulation
    private static void runSimulation(PurchaseOperation operation) throws InterruptedException {
        Thread[] threads = new Thread[15];

        // create 15 threads simulating high load
        for (int i = 0; i < 15; i++) {
            final int id = i;

            threads[i] = new Thread(() -> {
                operation.buy("Thread-" + id);
            });
        }

        // start all threads
        for (Thread t : threads) t.start();

        // wait for all to finish
        for (Thread t : threads) t.join();
    }

    // functional interface
    @FunctionalInterface
    interface PurchaseOperation {
        void buy(String threadName);
    }
}
