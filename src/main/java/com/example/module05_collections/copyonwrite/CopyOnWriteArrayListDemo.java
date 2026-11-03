package com.example.module05_collections.copyonwrite;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws InterruptedException {

        List<String> subscriberList = new CopyOnWriteArrayList<>();

        subscriberList.add("Subscriber 1");
        subscriberList.add("Subscriber 2");
        subscriberList.add("Subscriber 3");


        Thread readerThread = new Thread(() -> {
            for (String subscriber : subscriberList) {
                System.out.println(subscriber);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

        });

        Thread writerThread = new Thread(() -> {
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              return;
          }
            System.out.println("Writer thread is adding a new subscriber");
            subscriberList.add("Subscriber 4");
        });
        readerThread.start();
        writerThread.start();

        readerThread.join();
        writerThread.join();

        System.out.println("Final subscriber list: " + subscriberList);



    }
}
