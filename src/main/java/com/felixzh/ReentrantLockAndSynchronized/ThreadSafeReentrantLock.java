package com.felixzh.ReentrantLockAndSynchronized;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeReentrantLock {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Person2 person2 = new Person2();
        person2.setReentrantLock(reentrantLock);

        Thread writeThread = new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    person2.getReentrantLock().lock();
                    if (person2.isFlag()) {
                        try {
                            condition.await(); // 使线程休眠，作用等于synchronize中的thread.wait()方法
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (count == 0) {
                        person2.setName("小明2");
                        person2.setGender("男");
                    } else {
                        person2.setName("小红2");
                        person2.setGender("女");
                    }
                    count = (count + 1) % 2;
                    person2.setFlag(true);
                    condition.signal();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    person2.getReentrantLock().unlock(); // 必须手动关闭线程
                }
            }
        });
        writeThread.start();

        Thread readThread = new Thread(() -> {
            while (true) {
                try {
                    person2.getReentrantLock().lock();
                    if (!person2.isFlag()) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(person2.getName() + " : " + person2.getGender());
                    person2.setFlag(false);
                    condition.signal();//唤醒线程
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    person2.getReentrantLock().unlock();
                }
            }
        });
        readThread.start();
    }
}
