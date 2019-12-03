package ReentrantLock_FairLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * new ReentrantLock(true) 实现公平锁
 * */
public class ReentrantLockFairLock {

    private ReentrantLock reentrantLock = new ReentrantLock(true);

    private void testMyFairLock() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + ": get lock");
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockFairLock myFairLock = new ReentrantLockFairLock();
        Runnable runnable = () -> {
            //@Override
            //public void run() {
                System.out.println(Thread.currentThread().getName() + ": start");
                myFairLock.testMyFairLock();
            //}
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

}
