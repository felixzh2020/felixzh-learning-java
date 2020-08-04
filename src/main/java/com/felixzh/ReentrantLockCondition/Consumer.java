package com.felixzh.ReentrantLockCondition;

import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private LockConditionTest lockConditionTest;

    Consumer(LockConditionTest lockConditionTest) {
        this.lockConditionTest = lockConditionTest;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : consumer : " + lockConditionTest.consume());
        }
    }
}
