package com.sanrawat.raceconditionsafe;
public class CounterSynchronized{

    private int value=0; // shared variable

    public synchronized void increment(){
        value++;  //  Now atomic → due to synchronized
    }
    public  synchronized int getValue(){
        return value;
    }
}
