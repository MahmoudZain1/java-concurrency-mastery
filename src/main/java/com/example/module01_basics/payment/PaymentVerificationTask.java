package com.example.module01_basics.payment;

import java.util.concurrent.Callable;

public class PaymentVerificationTask implements Callable<String> {


    @Override
    public String call() throws Exception {
        Thread.sleep(5000);
        return "Payment verified successfully";
    }
}
