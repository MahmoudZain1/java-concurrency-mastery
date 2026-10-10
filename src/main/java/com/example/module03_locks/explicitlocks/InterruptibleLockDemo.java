package com.example.module03_locks.explicitlocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptibleLockDemo {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread 1 Successfully acquired the lock");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        } , "Thread-1");

        Thread thread2 =  new Thread(() -> {
            boolean acquired = false;
            try {
                lock.lockInterruptibly();
                acquired = true;
                System.out.println("Thread 2 Successfully acquired the lock");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread 2 interrupted while waiting for the lock");
            } finally {
                if (acquired) {
                    lock.unlock();
                }
            }
        } , "Thread-2");

        thread1.start();
        Thread.sleep(1000); // Allow thread1 to acquire the lock
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Threads completed");

    }
}
