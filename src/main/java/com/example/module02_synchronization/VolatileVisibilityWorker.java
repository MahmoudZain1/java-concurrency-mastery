package com.example.module02_synchronization;

public class VolatileVisibilityWorker {

    /*
     if you remove volatile,
     the worker thread may not see the updated value of isRunning in a timely manner
     */
    volatile boolean isRunning = true;


    public void startWorker(){
        System.out.println("Worker started.");

        while (isRunning) {
     //  Simulate some work
         }
        System.out.println("Worker stopped.");
    }

    public  void stopWorker(){
        isRunning = false;
    }
}
