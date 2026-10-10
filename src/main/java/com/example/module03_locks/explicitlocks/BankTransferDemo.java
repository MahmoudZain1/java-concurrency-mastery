package com.example.module03_locks.explicitlocks;

public class BankTransferDemo {

    public static void main(String[] args) throws InterruptedException {

        Account accountA =  new Account("A", 1000);
        Account accountB =  new Account("B", 1000);

        System.out.println("Details Before transfer");
        System.out.println( accountA.getAccountNumber() + " Balance =  " + accountA.getBalance());
        System.out.println( accountB.getAccountNumber() + " Balance = " + accountB.getBalance());

        Thread thread1 = new Thread(() -> {
            try {
                boolean transfer = accountA.transferMoney(accountB, 500);
                System.out.println("Transfer successful: " + transfer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread 1 interrupted");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                boolean transfer = accountB.transferMoney(accountA, 500);
                System.out.println("Transfer successful: " + transfer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread 2 interrupted");
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Details After transfer");
        System.out.println( accountA.getAccountNumber() + " Balance =  " + accountA.getBalance());
        System.out.println( accountB.getAccountNumber() + " Balance = " + accountB.getBalance());

    }
}
