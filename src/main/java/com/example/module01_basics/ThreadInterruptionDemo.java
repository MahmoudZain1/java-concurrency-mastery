package com.example.module01_basics;

public class ThreadInterruptionDemo {

    public static void main(String[] args) {
        InterruptibleTask task = new InterruptibleTask();
        Thread thread = new Thread(task);
        System.out.println("Starting the worker thread...");
        thread.start();

        try {
            Thread.sleep(5000); // Let the worker thread run for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sending interrupt signal to the worker thread...");
        thread.interrupt(); // Interrupt the worker thread
    }
}
