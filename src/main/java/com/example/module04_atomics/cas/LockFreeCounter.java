package com.example.module04_atomics.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeCounter {

    private final AtomicInteger value =  new AtomicInteger(0);


    public int increment(){
        int existingValue;
        int newValue;
        do{
            existingValue = value.get();
            newValue = existingValue + 1;
        } while (!value.compareAndSet(existingValue, newValue));
        return newValue;
    }

    public int decrement(){
        int existingValue;
        int newValue;
        do{
            existingValue = value.get();
            newValue = existingValue - 1;
        } while (!value.compareAndSet(existingValue, newValue));
        return newValue;
    }

    public int getValue(){
        return value.get();
    }
}

