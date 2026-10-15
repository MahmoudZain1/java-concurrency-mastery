package com.example.module04_atomics.stack;

public class LockFreeStackDemo {

    public static void main(String[] args) throws InterruptedException {
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        int operationsPerThread = 1000;


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < operationsPerThread; i++) {
                stack.push(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < operationsPerThread; i++) {
                stack.push(i);
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();


        int actualCount = 0;
        while (stack.pop() != null) {
            actualCount++;
        }

        System.out.println("Expected items in stack : " + (operationsPerThread * 2));
        System.out.println("Actual items in stack   : " + actualCount);
        System.out.println("Is Stack Empty now?     : " + stack.isEmpty());

    }
}
