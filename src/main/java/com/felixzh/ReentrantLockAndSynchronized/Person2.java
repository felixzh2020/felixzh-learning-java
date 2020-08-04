package com.felixzh.ReentrantLockAndSynchronized;

import java.util.concurrent.locks.ReentrantLock;

public class Person2 {
    private String name;
    private String gender;
    private boolean flag;
    private ReentrantLock reentrantLock;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public boolean isFlag() {
        return flag;
    }

    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }
}
