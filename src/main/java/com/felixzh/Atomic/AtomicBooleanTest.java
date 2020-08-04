package com.felixzh.Atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest extends Thread {

    private String name;
    private static AtomicBoolean exists = new AtomicBoolean(false);

    private AtomicBooleanTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if (exists.compareAndSet(false, true)) {
            System.out.println(String.format("name: %s enter", name));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(String.format("name: %s leave", name));
            exists.set(false);
        } else {
            System.out.println(String.format("name: %s give up", name));
        }
    }

    public static void main(String[] args) {
        AtomicBooleanTest atomicBooleanTest1 = new AtomicBooleanTest("bar1");
        AtomicBooleanTest atomicBooleanTest2 = new AtomicBooleanTest("bar2");
        atomicBooleanTest1.start();
        atomicBooleanTest2.start();
    }
}
