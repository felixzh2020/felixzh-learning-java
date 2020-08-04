package com.felixzh.ShutdownHook;

public class LongTask implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("This is a long task");
            Thread.sleep(1000 * 30);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
