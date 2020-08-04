package com.felixzh.OperatingSystem;

import java.util.Locale;

/**
 * @author felixzh
 *
 * from kafka source
 * 一个判断操作系统是否为windows的静态类
 * */
public class OperatingSystem {
    public OperatingSystem() {
    }

    private static final String NAME;
    private static final boolean IS_WINDOWS;

    static {
        NAME = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        IS_WINDOWS = NAME.startsWith("windows");
    }

    public static void main(String [] args){
        System.out.println(OperatingSystem.NAME);
        System.out.println(OperatingSystem.IS_WINDOWS);
    }
}
