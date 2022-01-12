package ScheduledThreadPoolExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author FelixZh
 * <p>
 * 该case背景描述：阅读Kafka源码KafkaServer类的时候，发现该类会启动一个KafkaScheduler类。
 * KafkaSchedule类基于java.util.concurrent.ScheduledThreadPoolExecutor实现，统一线程前缀kafka-scheduler-。
 * 该case属于Kafka源码KafkaScheduler类实现的变种方式。
 */

public class ScheduledThreadPoolExecutorCase {
    public static void main(String[] args) throws Exception {
        final String THREAD_NAME_PREFIX = "kafka-scheduler-";
        AtomicInteger schedulerThreadId = new AtomicInteger(0);
        boolean daemon = true;
        int corePoolSize = 5;
        int taskNum = 10;

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        /**
         *     public ScheduledThreadPoolExecutor(int corePoolSize) {
         *         super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
         *               new DelayedWorkQueue());
         *     }
         * */
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        executor.setRemoveOnCancelPolicy(true);
        //executor.prestartAllCoreThreads();
        executor.setThreadFactory(r -> new KafkaThreadCase(THREAD_NAME_PREFIX + schedulerThreadId.getAndIncrement(), r, daemon));

        for (int i = 0; i < taskNum; i++) {

            int finalI = i;
            executor.schedule(() -> {
                System.out.println("start before " + finalI + " size: " + executor.getQueue().size());
                System.out.println(finalI);
                try {
                    Thread.sleep(10_000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("start after " + finalI + " size: " + executor.getQueue().size());
            }, 0, TimeUnit.SECONDS);


        }

        //确保线程池线程执行完成，顺便打印下线程池基本信息
        while (true) {
            System.out.println("====size: " + executor.getQueue().size());
            Thread.sleep(3_000);
        }

    }
}
