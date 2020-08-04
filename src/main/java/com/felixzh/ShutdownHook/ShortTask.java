package com.felixzh.ShutdownHook;

public class ShortTask implements Runnable{
    @Override
    public void run() {
        System.out.println("This is a short task");
    }
}
