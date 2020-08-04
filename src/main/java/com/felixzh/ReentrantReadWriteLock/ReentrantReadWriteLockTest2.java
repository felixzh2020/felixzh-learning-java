package com.felixzh.ReentrantReadWriteLock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁是在重入锁ReentrantLock基础上的一大改造，其通过在重入锁上维护一个读锁一个写锁实现的。
 * 对于ReentrantLock和ReentrantreadWriteLock的使用需要在开发者自己根据实际项目的情况而定。
 * 对于读写锁当读的操作远远大于写操作的时候会增加程序很高的并发量和吞吐量。
 * 虽说在高并发的情况下，读写锁的效率很高，但是同时又会存在一些问题。
 * 比如当读并发很高时读操作长时间占有锁，导致写锁长时间无法被获取而导致的线程饥饿问题，
 * 因此在JDK1.8中又在ReentrantReadWriteLock的基础上新增了一个读写并发锁StampLock。
 *  什么是锁降级，锁降级就是从写锁降级成为读锁。在当前线程拥有写锁的情况下，再次获取到读锁，随后释放写锁的过程就是锁降级。
 */
public class ReentrantReadWriteLockTest2 {

    public static void main(String[] args) {
        Data readWriteData = new Data();
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> readWriteData.writeData(String.valueOf(new Random().nextInt(1000))));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            executorService.submit(() -> readWriteData.readData());
        }
    }
}

class Data {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String data;

    void writeData(String data) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("start write data : " + data);
            this.data = data;
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        readWriteLock.writeLock().unlock();
    }

    void readData() {
        readWriteLock.readLock().lock();
        try {
            System.out.println("start read data : " + this.data);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        readWriteLock.readLock().unlock();
    }

}
