package com.sanrawat.raceconditionsafe;

import java.util.concurrent.locks.ReentrantLock;

/*
          Supports tryLock(), fairness policies, interruptible locks
          More flexible than synchronized
          Useful in complex concurrency scenarios
* */
public class CounterSafeLock {

    // Shared resource (not thread-safe by itself)
    private int count = 0;

    // A ReentrantLock object that controls access to critical sections
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Increments the counter in a thread-safe way using ReentrantLock
     */
    public void increment() {

        // Acquire the lock before entering the critical section.
        // Only ONE thread can hold this lock at a time.
        lock.lock();

        try {
            // Critical section
            // This code is protected by the lock
            count++;

        } finally {
            // Always release the lock (even if an exception happens)
            // If we forget: other threads will be BLOCKED forever → deadlock
            lock.unlock();
        }
    }

    /**
     * Returns the count value in a thread-safe way
     */
    public int getCount() {

        // Acquire the lock because reading a shared variable
        // also needs protection if other threads are modifying it.
        lock.lock();

        try {
            return count;

        } finally {
            // Release lock so other threads can continue
            lock.unlock();
        }
    }
}

