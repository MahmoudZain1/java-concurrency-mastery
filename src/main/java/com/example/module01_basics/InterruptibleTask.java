package com.example.module01_basics;

public class InterruptibleTask implements  Runnable {


    @Override
    public void run() {
      int counter = 1;
      while (!Thread.currentThread().isInterrupted()) {
          System.out.println("Worker is Running... Task number: " + counter++);

          try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println("Worker thread interrupted during sleep.");
                Thread.currentThread().interrupt(); // Preserve the interrupt status
          }
      }
    }
}
