package com.felixzh.ReentrantLockCondition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {
    private LinkedList<String> queue = new LinkedList<>();

    private Lock lock = new ReentrantLock();

    private Condition providerCondition = lock.newCondition();

    private Condition consumerCondition = lock.newCondition();

    void provide(String value) {
        int maxSize = 5;
        try {
            lock.lock();
            while (queue.size() == maxSize) {
                providerCondition.await();
            }
            queue.add(value);
            consumerCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    String consume() {
        String result = null;
        try {
            lock.lock();
            while (queue.size() == 0) {
                consumerCondition.await();
            }
            result = queue.poll();
            providerCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public static void main(String[] args) {
        LockConditionTest t = new LockConditionTest();
        new Thread(new Provider(t)).start();
        new Thread(new Consumer(t)).start();
    }
}
