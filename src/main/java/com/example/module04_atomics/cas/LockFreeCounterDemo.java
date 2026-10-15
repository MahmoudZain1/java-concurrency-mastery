package com.example.module04_atomics.cas;

public class LockFreeCounterDemo {

    public static void main(String[] args) throws InterruptedException {

        LockFreeCounter counter = new LockFreeCounter();

        int number = 200000;

        System.out.println("Lock Free Increment Demo");

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < number; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < number; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int expectedCount = number * 2;

        System.out.println("Actual Value: " + counter.getValue());
        System.out.println("Expected Value: " + expectedCount);
    }
}