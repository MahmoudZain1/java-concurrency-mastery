package com.example.module02_synchronization;

public class VolatileVisibilityDemo {

    public static void main(String[] args) throws InterruptedException {

        VolatileVisibilityWorker worker = new VolatileVisibilityWorker();

        Thread t1 = new Thread(() -> worker.startWorker());
        t1.start();

        Thread.sleep(1000);

        System.out.println("Stopping worker...");
        worker.stopWorker();
    }
}
