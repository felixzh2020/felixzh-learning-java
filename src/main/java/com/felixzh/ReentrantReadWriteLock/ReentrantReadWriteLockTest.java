package com.felixzh.ReentrantReadWriteLock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock中维护了读锁和写锁。允许线程同时读取共享资源；但是如果有一个线程是写数据，那么其他线程就不能去读写该资源。
 * 即会出现三种情况：读读共享，写写互斥，读写互斥。
 * */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        Thread writeThread = new Thread(() -> {
            //writeLock.lock();
            readLock.lock();

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " : read");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            //writeLock.unlock();
            readLock.unlock();
        });
        writeThread.start();

        Thread readThread = new Thread(() -> {
//            readLock.lock();
            writeLock.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " : write");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            //readLock.unlock();
            writeLock.lock();
        });
        readThread.start();

    }

}
