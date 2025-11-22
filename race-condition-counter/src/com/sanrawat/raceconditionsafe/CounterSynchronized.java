package com.sanrawat.raceconditionsafe;

/*
*
         * Only one thread at a time can enter increment()
         * Prevents interleaving read-modify-write
         * Guaranteed consistency
         * Slower than AtomicInteger but simplest to understand
*
* */
public class CounterSynchronized{

    private int value=0; // shared variable

    public synchronized void increment(){
        value++;  //  Now atomic → due to synchronized
    }
    public  synchronized int getValue(){
        return value;
    }
}
