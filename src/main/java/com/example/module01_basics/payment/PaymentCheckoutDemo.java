package com.example.module01_basics.payment;

import java.util.concurrent.*;

public class PaymentCheckoutDemo {

    public static void main(String[] args) {

        PaymentVerificationTask paymentVerificationTask = new PaymentVerificationTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Future<String> futureResult = executorService.submit(paymentVerificationTask);

            while (!futureResult.isDone()) {
                System.out.println("Payment verification is in progress...");
                Thread.sleep(1000);
            }
            System.out.println(futureResult.get());



        }catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
        }finally {
            executorService.shutdown();
        }


    }
}
