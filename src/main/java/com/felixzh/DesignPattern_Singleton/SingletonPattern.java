package com.felixzh.DesignPattern_Singleton;

public class SingletonPattern extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":" + MySystem.getInstance().getDate());
    }

    public static void main(String[] args) {
        new SingletonPattern().start();
        new SingletonPattern().start();
    }

}
