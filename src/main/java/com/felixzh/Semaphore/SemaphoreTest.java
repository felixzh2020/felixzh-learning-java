package com.felixzh.Semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore类有两个重要方法
 * 1、semaphore.acquire();
 * 请求一个信号量，这时候信号量个数-1，当减少到0的时候，下一次acquire不会再执行，只有当执行一个release()的时候，信号量不为0的时候才可以继续执行acquire
 * <p>
 * 2、semaphore.release();
 * 释放一个信号量，这时候信号量个数+1，
 * <p>
 * 3、这个类使用的目的为：
 * 如何控制某个方法允许并发访问线程的个数？
 * 也就是说在线程里执行某个方法的时候，在方法里用该类对象进行控制，就能保证所有的线程中最多只有指定信号量个数个该方法在执行。
 */
public class SemaphoreTest {

    private static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                threadLog();
            }).start();
        }
    }

    private static void threadLog() {
        try {
            semaphore.acquire();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("线程:" + Thread.currentThread().getName() + "执行了一个acquire请求操作");

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        semaphore.release();
        System.out.println("线程:"+Thread.currentThread().getName()+"执行了一个release请求操作");
    }

}
