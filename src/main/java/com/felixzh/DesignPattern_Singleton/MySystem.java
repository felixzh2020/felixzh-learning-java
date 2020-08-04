package com.felixzh.DesignPattern_Singleton;

import java.util.Date;

class MySystem {
    private static MySystem instance = null;
    private Date date = new Date();

    Date getDate() {
        return date;
    }

    static MySystem getInstance() {
        if (instance == null) {//懒加载
            synchronized (MySystem.class) {
                instance = new MySystem();
            }
        }

        return instance;
    }
}
