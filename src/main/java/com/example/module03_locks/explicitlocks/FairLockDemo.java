package com.example.module03_locks.explicitlocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLockDemo {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired the lock");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }
            }
        };

        Thread thread1 =  new Thread(task, "Thread-a");
        Thread thread2 =  new Thread(task, "Thread-b");
        Thread thread3 =  new Thread(task, "Thread-c");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
