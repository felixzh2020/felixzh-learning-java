package com.felixzh.ReentrantLockAndSynchronized;

/**
 * 没有考虑线程安全问题，读写线程会竞争共享资源，导致数据紊乱。
 * */
public class UnThreadSafe {
    public static void main(String[] args) {
        Person person = new Person();

        Thread writeThread = new Thread(() -> {
            int count = 0;

            while (true) {
                if (count == 0) {
                    person.setName("张三");
                    person.setGender("男");
                } else {
                    person.setName("李四");
                    person.setGender("女");
                }

                person.setFlag(true);
                count = (count + 1) % 2;
            }
        });
        writeThread.start();

        Thread readThread = new Thread(() -> {
            while (true) {
                System.out.println(person.getName() + " : " + person.getGender());
            }
        });
        readThread.start();

    }
}
