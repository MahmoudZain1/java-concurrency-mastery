package com.example.module01_basics;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadCreationDemo {
    public static void main(String[] args) {

//        CustomRunnableTask task = new CustomRunnableTask();
//        Thread thread1 = new Thread(task , " Thread-1");
//        thread1.start();



        CustomThreadTask thread2 = new CustomThreadTask();
        thread2.setName("Thread-2");
        System.out.println("Thread-2 created: " + thread2.getState());
        thread2.start();
        System.out.println("Thread-2 started: " + thread2.getState());

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main application");


        System.out.println("Thread-2 completed: " + thread2.getState());

        CustomCallableTask callableTask = new CustomCallableTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> futureResult = executorService.submit(callableTask);
        try {
            Integer result = futureResult.get();
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();

        }


    }
}
