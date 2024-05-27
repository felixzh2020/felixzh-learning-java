package com.felixzh.learning;

import java.util.ArrayList;
import java.util.List;

/**
 * java -Xmx200m -Xms200m -Xss5m -XX:MaxDirectMemorySize=10m -XX:NativeMemoryTracking=detail -cp NativeMemoryTrack-1.0.jar com.felixzh.learning.Main
 */
public class Main {
    public static void main(String[] args) {
        List<Thread> list = new ArrayList<>(10000);
        int num = 500;
        while (num-- > 0) {
            Thread thread = new Thread(() -> method(0));
            thread.start();
            list.add(thread);
        }
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void method(long i) {
        int loop = 1024 * 16;
        if (i < loop) {
            method(++i);
        } else {
            try {
                System.out.println(i);
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}