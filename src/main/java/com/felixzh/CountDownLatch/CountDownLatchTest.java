package com.felixzh.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String [] args){
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new RunTask(countDownLatch));
        executorService.submit(new RunTask(countDownLatch));

        executorService.shutdown();

        System.out.println("等待两个线程执行完毕…… ……");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("两个子线程都执行完毕，继续执行主线程");
    }
}
