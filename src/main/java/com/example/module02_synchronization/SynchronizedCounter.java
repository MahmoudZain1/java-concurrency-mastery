package com.example.module02_synchronization;

public class SynchronizedCounter {

    private int count = 0;

    private final Object lock = new Object();


    public void unsafeIncrement() {
        count++;  //Read -> Modify -> Write
    }


    public synchronized  void synchronizedMethodIncrement() {
        count++;
    }

    public void synchronizedBlockIncrement() {
        synchronized (lock) {
            count++;
        }
    }

    public  int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }

}

