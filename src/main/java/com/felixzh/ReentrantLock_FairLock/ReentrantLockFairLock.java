package com.felixzh.ReentrantLock_FairLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * new ReentrantLock(true) 实现公平锁
 * */
public class ReentrantLockFairLock {

    private ReentrantLock reentrantLock = new ReentrantLock(true);

    private void testFairLock() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + ": get lock");
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockFairLock myFairLock = new ReentrantLockFairLock();
        Runnable runnable = () -> {
            //@Override
            //public void run() {
                System.out.println(Thread.currentThread().getName() + ": start");
                myFairLock.testFairLock();
            //}
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

}
