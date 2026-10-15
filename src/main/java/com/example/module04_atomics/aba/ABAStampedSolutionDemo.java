package com.example.module04_atomics.aba;

import java.util.concurrent.atomic.AtomicStampedReference;

public class ABAStampedSolutionDemo {

    private static final AtomicStampedReference<String> sharedValue = new AtomicStampedReference<>("A", 0);


    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            int[] stampHolder = new int[1];
            String oldValue = sharedValue.get(stampHolder);
            System.out.println("Thread 1 read value: " + oldValue + " with stamp: " + stampHolder[0]);

            // Simulate some work
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            boolean success = sharedValue.compareAndSet(oldValue, "B", stampHolder[0], stampHolder[0] + 1);
            System.out.println("Thread 1 attempted to change value to B: " + success);
        });

        Thread thread2 = new Thread(() -> {
            int[] stampHolder = new int[1];
            String oldValue = sharedValue.get(stampHolder);
            System.out.println("Thread 2 read value: " + oldValue + " with stamp: " + stampHolder[0]);

            // Simulate some work
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sharedValue.set("C", stampHolder[0] + 1);
            System.out.println("Thread 2 changed value to C with new stamp: " + (stampHolder[0] + 1));
            sharedValue.set("A", stampHolder[0] + 2);
            System.out.println("Thread 2 changed value back to A with new stamp: " + (stampHolder[0] + 2));
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }
}
