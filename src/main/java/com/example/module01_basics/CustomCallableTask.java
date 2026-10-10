package com.example.module01_basics;

import java.util.concurrent.Callable;

public class CustomCallableTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i = 1; i <= 5; i++){
            sum += i;
            System.out.println("Current sum: " + sum);
            Thread.sleep(2000); // Sleep for 2 seconds
        }
        return sum;
    }
}
