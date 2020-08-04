package com.felixzh.multiThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("getQueue:" + threadPool.getQueue().size());
        System.out.println("remainingCapacity:" + threadPool.getQueue().remainingCapacity());

        threadPool.execute(() -> {
            try {
                int count = 0;
                Thread.currentThread().setName("aa");
                while (count <= 10) {
                    System.out.println(Thread.currentThread().getName() + "getQueue:" + threadPool.getQueue().size());
                    System.out.println(Thread.currentThread().getName() + System.currentTimeMillis());
                    Thread.sleep(1000);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                int count = 0;
                Thread.currentThread().setName("bbb");
                while (count <= 100) {
                    System.out.println(Thread.currentThread().getName() + "getQueue:" + threadPool.getQueue().size());
                    System.out.println(Thread.currentThread().getName() + System.currentTimeMillis());
                    Thread.sleep(1000);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
