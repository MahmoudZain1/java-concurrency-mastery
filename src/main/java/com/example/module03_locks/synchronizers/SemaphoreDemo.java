package com.example.module03_locks.synchronizers;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    private static final class DatabaseConnectionPool{
        private final int maxConnections = 3;
        Semaphore semaphore =  new Semaphore(maxConnections , true);

        public void useConnection(String taskName){

            try {
                System.out.println(taskName + " is waiting for a connection");
                semaphore.acquire();

                try {
                    System.out.println(taskName + " has acquired a connection");
                    Thread.sleep(2000);
                    System.out.println(taskName + " has finished using the connection");

                } finally {
                    semaphore.release();
                    System.out.println("" + taskName + " has released the connection " +
                            "Available permits: " + semaphore.availablePermits());

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {

        DatabaseConnectionPool pool = new DatabaseConnectionPool();

        Runnable clientTask1 = () -> pool.useConnection("clint-1");
        Runnable clientTask2 = () -> pool.useConnection("clint-2");
        Runnable clientTask3 = () -> pool.useConnection("clint-3");
        Runnable clientTask4 = () -> pool.useConnection("clint-4");


        Thread thread1 = new Thread(clientTask1 , "Thread-1");
        Thread thread2 = new Thread(clientTask2 , "Thread-2");
        Thread thread3 = new Thread(clientTask3 , "Thread-3");
        Thread thread4 = new Thread(clientTask4 , "Thread-4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println("All client tasks have completed");

    }
}
