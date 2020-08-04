package com.felixzh.ReentrantLockCondition;

import java.util.concurrent.TimeUnit;

public class Provider implements Runnable {

    private LockConditionTest lockConditionTest;

    Provider(LockConditionTest lockConditionTest) {
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

            String value = String.valueOf(System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + " : provider : " + value);
            lockConditionTest.provide(value);
        }
    }
}
