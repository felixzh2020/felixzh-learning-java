package ScheduledThreadPoolExecutor;

/**
 * 多种使用方法：静态方法(类方法)、实例方法(构造方法)
 */
public class KafkaThreadCase extends Thread {

    public static KafkaThreadCase daemon(final String name, Runnable runnable) {
        return new KafkaThreadCase(name, runnable, true);
    }

    public static KafkaThreadCase nonDaemon(final String name, Runnable runnable) {
        return new KafkaThreadCase(name, runnable, false);
    }


    public KafkaThreadCase(final String name, Runnable runnable, boolean daemon) {
        super(runnable, name);
        configureThread(name, daemon);
    }

    private void configureThread(final String name, boolean daemon) {
        setDaemon(daemon);
        setUncaughtExceptionHandler((t, e) -> System.out.println(name + " : " + e));
    }
}
