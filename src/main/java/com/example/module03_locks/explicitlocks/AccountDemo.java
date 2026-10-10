package com.example.module03_locks.explicitlocks;

public class AccountDemo {

    public static void main(String[] args) throws InterruptedException {


        Account accountA =  new Account("A", 1000);
        Account accountB =  new Account("B", 500);

        System.out.println("Before transfer");

        System.out.println( accountA.getAccountNumber() + " Balance =  " + accountA.getBalance());
        System.out.println( accountB.getAccountNumber() + " Balance = " + accountB.getBalance());

        boolean transfer =  accountA.transferMoney(accountB, 500);
        System.out.println("Transfer successful: " + transfer);


        System.out.println("After transfer");
        System.out.println( accountA.getAccountNumber() + " Balance =  " + accountA.getBalance());
        System.out.println( accountB.getAccountNumber() + " Balance = " + accountB.getBalance());

    }

}
