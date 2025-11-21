package com.sanrawat.raceconditionunsafe;

public class Counter {

    private int value=0; // shared variable

    public void increment(){
        value++;  //  Not atomic → causes race condition
    }
    public  int getValue(){
        return value;
    }
}
