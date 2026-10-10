package com.example.module03_locks.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    private static final class ProductCatalog{
        private final Map<String , Double> prices = new HashMap<>();
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

        public ProductCatalog() {
            prices.put("Java Book", 49.99);
            prices.put("Design-Patterns-Book", 99.99);
        }

        public Optional<Double> getPrice(String product) {
            rwLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " started reading " + product);
                Thread.sleep(1000);
                return Optional.ofNullable(prices.get(product));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return Optional.empty();
            } finally {
                rwLock.readLock().unlock();
            }
        }

        public void updatePrice (String product , double price) {
            rwLock.writeLock().lock();
            try{
                System.out.println(Thread.currentThread().getName() + " started updating " + product);
                Thread.sleep(2000);
                prices.put(product , price);
                System.out.println(Thread.currentThread().getName() + " updated " + product + " to " + price);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                rwLock.writeLock().unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ProductCatalog productCatalog =  new ProductCatalog();

        Thread reader1 = new Thread(() ->{
        Optional<Double> price = productCatalog.getPrice("Java Book");
        System.out.println("Reader-1 got : " + price.orElse(0.0));
        }, "Reader-1" );

        Thread reader2 = new Thread(() ->{
              Optional<Double> price = productCatalog.getPrice("Design-Patterns-Book");
              System.out.println("Reader-2 got : " + price.orElse(0.0));
            }, "Reader-2");


        Thread writer = new Thread(() -> productCatalog.updatePrice("Java Book", 59.99), "Writer");

        reader1.start();
        reader2.start();
        Thread.sleep(200);
        writer.start();


        reader1.join();
        reader2.join();
        writer.join();

        System.out.println("Final price of Java Book: " + productCatalog.getPrice("Java Book"));

    }
}
