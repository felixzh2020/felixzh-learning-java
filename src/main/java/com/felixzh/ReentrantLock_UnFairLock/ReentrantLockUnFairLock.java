package com.felixzh.ReentrantLock_UnFairLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * new ReentrantLock(false) 实现非公平锁
 */
public class ReentrantLockUnFairLock {

    private ReentrantLock reentrantLock = new ReentrantLock(false);

    private void testUnFairLock() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + ": get lock");
            //TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockUnFairLock reentrantLockUnFairLock = new ReentrantLockUnFairLock();

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + ": start");
            reentrantLockUnFairLock.testUnFairLock();
        };

        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
        /*for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }*/
    }

}
