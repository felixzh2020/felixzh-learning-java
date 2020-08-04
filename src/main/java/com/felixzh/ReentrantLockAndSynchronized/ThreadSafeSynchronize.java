package com.felixzh.ReentrantLockAndSynchronized;

/**
 * 线程安全（加synchronize关键字）
 */
public class ThreadSafeSynchronize {
    public static void main(String[] args) {
        Person person = new Person();

        Thread writeThread = new Thread(() -> {
            int count = 0;
            while (true) {
                synchronized (person) {
                    if (person.isFlag()) {
                        try {
                            /* Causes the current thread to wait until another thread invokes the
                             * {@link java.lang.Object#notify()} method or the
                             * {@link java.lang.Object#notifyAll()} method for this object.
                             */
                            person.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (count == 0) {
                        person.setName("小明");
                        person.setGender("男");
                    } else {
                        person.setName("小红");
                        person.setGender("女");
                    }
                    count = (count + 1) % 2;
                    person.setFlag(true);
                    person.notify();
                }
            }
        });
        writeThread.start();

        Thread readThread = new Thread(() -> {
            while (true) {
                synchronized (person) {
                    if (!person.isFlag()) {
                        try {
                            person.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(person.getName() + " : " + person.getGender());
                    person.setFlag(false);
                    person.notify();
                }
            }
        });
        readThread.start();
    }
}
