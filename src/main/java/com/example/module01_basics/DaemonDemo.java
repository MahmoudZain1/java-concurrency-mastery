package com.example.module01_basics;

public class DaemonDemo {

    public static void main(String[] args) {



        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread is running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("Main thread is running...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread is exiting, daemon thread will be terminated.");
    }
}
