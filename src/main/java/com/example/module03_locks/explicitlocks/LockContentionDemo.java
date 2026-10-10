package com.example.module03_locks.explicitlocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockContentionDemo {

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {

                Thread.sleep(2000);
                System.out.println("Thread 1 releasing the lock");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread 1 interrupted");
            } finally {
                lock.unlock();
            }

        });

        Thread thread2 = new Thread(() -> {


            try {
                boolean require = lock.tryLock(500, TimeUnit.MILLISECONDS);

                if (require) {
                    try {
                        System.out.println("Thread 2 acquired the lock");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Thread 2 could not acquire the lock");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread 2 interrupted");
            }

        });

        System.out.println("Starting threads");
        thread1.start();
        Thread.sleep(100);
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Threads completed");
    }
}
