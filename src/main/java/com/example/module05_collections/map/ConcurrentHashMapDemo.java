package com.example.module05_collections.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

        int TheThreadCount = 10;
        int TheRequestPerThread = 1000;
        String targetUser = "user_service";

        Thread[] threads = new Thread[TheThreadCount];
        for(int i = 0; i < TheThreadCount; i++) {
            threads[i] = new Thread(() -> {
                for(int j = 0; j < TheRequestPerThread; j++) {
                    map.merge(targetUser, 1L, Long::sum);
                }
            });
            threads[i].start();
        }

        for(Thread t : threads){
            t.join();

        }

        long expected = TheThreadCount * TheRequestPerThread;
        long result = map.getOrDefault(targetUser, 0L);

        System.out.println("Expected: " + expected);
        System.out.println("Result: " + result);

        if(expected == result) {
            System.out.println("Test passed!");
        }
    }
}
