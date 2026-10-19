package com.example.module04_atomics.adders;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderVsAtomicLongDemo {
        private static final int THREAD_COUNT = 10;
    private static final int INCREMENTS = 10_000_000;


    public static void main(String[] args) throws InterruptedException {

        AtomicLong atomicLong =  new AtomicLong(0);
        CountDownLatch countDownLatch =  new CountDownLatch(1);
        Thread[] threads =  new Thread[THREAD_COUNT];


        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < INCREMENTS; j++) {
                    atomicLong.incrementAndGet();
                }
            });
            threads[i].start();
        }
        long startAtomic = System.nanoTime();
        countDownLatch.countDown();
        for (Thread t : threads) {
            t.join();
        }
        long endAtomic = System.nanoTime();

        System.out.println("[AtomicLong]");
        System.out.println("Execution Time: " + TimeUnit.NANOSECONDS.toMillis(endAtomic - startAtomic) + " ms");
        System.out.println("Final Value   : " + atomicLong.get());



        LongAdder longAdder = new LongAdder();
        CountDownLatch startAdderLatch = new CountDownLatch(1);
        Thread[] adderThreads = new Thread[THREAD_COUNT];


        for(int i = 0; i < THREAD_COUNT; i++) {
            adderThreads[i] = new Thread(() -> {
                try {
                    startAdderLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < INCREMENTS; j++) {
                    longAdder.increment();
                }
            });
            adderThreads[i].start();
        }
        long startAdder = System.nanoTime();
        startAdderLatch.countDown();
        for (Thread t : adderThreads) {
            t.join();
        }
        long endAdder = System.nanoTime();

        System.out.println("[LongAdder]");
        System.out.println("Execution Time: " + TimeUnit.NANOSECONDS.toMillis(endAdder - startAdder) + " ms");
        System.out.println("Final Value   : " + longAdder.sum());
    }
}
