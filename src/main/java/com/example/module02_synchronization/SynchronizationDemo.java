package com.example.module02_synchronization;

public class SynchronizationDemo {

    public static void main(String[] args) throws InterruptedException {

        SynchronizedCounter counter =  new SynchronizedCounter();

        int number = 200000;

        System.out.println("Unsafe Increment Demo");
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < number; i++) {
                counter.unsafeIncrement();
            }
        });

        Thread t2 =  new Thread(() -> {
            for(int i = 0; i < number; i++) {
                counter.unsafeIncrement();
            }
        });


        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Unsafe Increment Count: " + counter.getCount());


        System.out.println("\nSafe Increment Demo");

        counter.resetCount();

        Thread t3 = new Thread(() -> {
            for(int i = 0; i < number; i++) {
                counter.synchronizedBlockIncrement();
            }
        });

        Thread t4 = new Thread(() -> {
            for(int i = 0; i < number; i++) {
                counter.synchronizedBlockIncrement();
            }
        });

        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("Safe Increment Count: " + counter.getCount());

    }
}
