package com.example.module03_locks.explicitlocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final String accountNumber;
    private double balance;
    private final Lock lock = new ReentrantLock();



    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        lock.lock();
        try {

            return balance;
        } finally {
            lock.unlock();
        }
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        lock.lock();
        try {
            // Critical section
            balance += amount;
        } finally {
            lock.unlock();
        }

    }


    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        lock.lock();
        try {
            if (balance < amount) {
                return false;
            }
            balance -= amount;
            return true;

        } finally {
            lock.unlock();
        }
    }

    public boolean transferMoney(Account to, double amount)
            throws InterruptedException {

        if (to == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (to == this) {
            return false;
        }

        boolean sourceLocked = lock.tryLock();

        if (!sourceLocked) {
            return false;
        }

        try {
            boolean targetLocked = to.lock.tryLock(50 , TimeUnit.MILLISECONDS);

            if (!targetLocked) {
                return false;
            }

            try {
                //Critical Section
                if (this.balance < amount){
                    return false;
                }
                this.balance -= amount;
                to.balance += amount;
                return true;
            } finally {
                to.lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

}
