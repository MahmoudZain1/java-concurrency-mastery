package com.example.module03_locks.synchronizers;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {


        CountDownLatch latch = new CountDownLatch(3);



        Thread dbThread =  new Thread( () -> {
            try {
                System.out.println("Database thread is running");
                Thread.sleep(3000);
                System.out.println("Database thread has finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                latch.countDown();
            }
        }  , "Database-Thread");



        Thread secThread =  new Thread( () -> {
            try {
                System.out.println("Security thread is running");
                Thread.sleep(1000);
                System.out.println("Security thread has finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                latch.countDown();
            }
        } , "Security-Thread");


        Thread cacheThread =  new Thread( () -> {
            try {
                System.out.println("Cache thread is running");
                Thread.sleep(2000);
                System.out.println("Cache thread has finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                latch.countDown();
            }
        } , "Cache-Thread");


        dbThread.start();
        secThread.start();
        cacheThread.start();


        try {
            System.out.println("Main thread is waiting for other threads to finish");
            latch.await();
            System.out.println("All threads have finished, main thread is proceeding");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }
}
