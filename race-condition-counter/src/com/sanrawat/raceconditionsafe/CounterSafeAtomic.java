package com.sanrawat.raceconditionsafe;

import java.util.concurrent.atomic.AtomicInteger;


/*
*
     Uses CAS (Compare-And-Set) operation internally
     No locking → faster than synchronized
     Best for high-performance counters
*
* */
public class CounterSafeAtomic {

    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // atomic
    }

    public int getCount() {
        return count.get();
    }

}
