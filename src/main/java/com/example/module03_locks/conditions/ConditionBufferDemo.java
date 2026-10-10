package com.example.module03_locks.conditions;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBufferDemo {

    private static final class boundedBuffer<T> {

        private final Queue<T> queue =  new ArrayDeque<>(10);
        private final int capacity ;
        private Lock lock =  new ReentrantLock();
        private Condition notFull = lock.newCondition();
        private Condition notEmpty = lock.newCondition();


        private boundedBuffer(int capacity) {
            this.capacity = capacity;
        }


        public void put(T item) throws InterruptedException{
            lock.lockInterruptibly();
            try {
                if (queue.size() == capacity) {
                    notFull.await();
                }
                queue.add(item);
                System.out.println(Thread.currentThread().getName() + " produced " + item);
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }


        public T take () throws InterruptedException{
            lock.lockInterruptibly();
            try{
                if(queue.isEmpty()){
                    notEmpty.await();
                }
                T item = queue.remove();
                System.out.println(Thread.currentThread().getName() + " consumed " + item);
                notFull.signal();
                return item;
            }finally {
                lock.unlock();
            }

        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        boundedBuffer<Integer> buffer = new boundedBuffer<>(5);

        Thread Producer =  new Thread(()-> {
            for (int i = 1; i < 5; i++) {
                try {
                    buffer.put(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } , "Producer");

        Thread Consumer =  new Thread(()-> {
            for (int i = 1; i < 5; i++) {
                try {
                    buffer.take();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } , "Consumer");

        Producer.start();
        Consumer.start();
        Producer.join();
        Consumer.join();

    }
}
