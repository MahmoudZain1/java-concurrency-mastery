package com.example.module04_atomics.aba;

import java.util.concurrent.atomic.AtomicReference;

public class ABASimulationDemo {

    private static final AtomicReference<String> sharedValue = new AtomicReference<>("A");


    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            String oldValue = sharedValue.get();
            System.out.println("Thread 1 read value: " + oldValue);

            // Simulate some work

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            boolean success = sharedValue.compareAndSet(oldValue, "B");
            System.out.println("Thread 1 attempted to change value to B: " + success);
        });

        Thread thread2 = new Thread(() -> {
            String oldValue = sharedValue.get();
            System.out.println("Thread 2 read value: " + oldValue);

            // Simulate some work
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sharedValue.set("C");
            System.out.println("Thread 2 changed value to C");
            sharedValue.set("A");
            System.out.println("Thread 2 changed value back to A");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


        System.out.println("Final value: " + sharedValue.get());
    }
}
