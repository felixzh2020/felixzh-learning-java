package com.felixzh.Atomic;

import java.util.concurrent.TimeUnit;

public class BooleanTest extends Thread {

    private String name;
    private static boolean exists = false;

    private BooleanTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if (!exists) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            exists = true;
            System.out.println(String.format("name: %s enter", name));

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println(String.format("name: %s leave", name));
            exists = false;
        } else {
            System.out.println(String.format("name: %s give up", name));
        }
    }

    public static void main(String[] args) {
        BooleanTest booleanTest1 = new BooleanTest("bar1");
        BooleanTest booleanTest2 = new BooleanTest("bar2");
        booleanTest1.start();
        booleanTest2.start();
    }
}
