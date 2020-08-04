package com.felixzh.ShutdownHook;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TestThreadFactory implements ThreadFactory {
    private String threadName;
    private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

    public TestThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        final Thread t = defaultFactory.newThread(r);
        t.setName(threadName);
        return t;
    }
}
