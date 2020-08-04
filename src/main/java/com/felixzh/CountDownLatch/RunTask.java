package com.felixzh.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RunTask implements Runnable{
    private CountDownLatch countDownLatch;

    public RunTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            TimeUnit.SECONDS.sleep(3);
            System.out.println("子线程："+Thread.currentThread().getName()+"执行");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        countDownLatch.countDown();
    }
}
