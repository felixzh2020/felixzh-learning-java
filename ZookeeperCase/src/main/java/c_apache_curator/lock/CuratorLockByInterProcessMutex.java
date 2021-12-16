package c_apache_curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author FelixZh
 * <p>
 * 该类主要介绍 Curator 分布式锁 使用方法
 * 锁类型包括：InterProcessMutex、InterProcessMultiLock、InterProcessReadWriteLock、InterProcessSemaphoreMutex
 * 均实现 interface InterProcessLock
 */
public class CuratorLockByInterProcessMutex {

    public static void main(String[] args) {
        //连接超时
        int CONNECTION_TIMEOUT_MS = 15_000;
        //会话超时
        int SESSION_TIMEOUT_MS = 60_000;
        //Zookeeper IP:PORT
        String CONNECT_STRING = "felixzh:2181";
        //重试次数
        int MAX_RETRIES = 3;
        //重试时间间隔
        int BASE_SLEEP_TIME_MS = 1000;
        //lock path
        String ZOOKEEPER_LOCK_PATH = "/felixzh";

        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES);
        //通过工厂创建Zookeeper framework-style client
        CuratorFramework cfClient = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(CONNECTION_TIMEOUT_MS)
                .sessionTimeoutMs(SESSION_TIMEOUT_MS)
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
                .build();
        //Start the client. Most mutator methods will not work until the client is started
        cfClient.start();

        //工作线程1，获取锁，do something
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lockByInterProcessMutex(cfClient, ZOOKEEPER_LOCK_PATH);
            }
        }, "thread1");
        //工作线程2，获取锁，do something
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lockByInterProcessMutex(cfClient, ZOOKEEPER_LOCK_PATH);
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }

    private static void lockByInterProcessMutex(CuratorFramework cfClient, String zkLockPath) {
        InterProcessMutex lock = new InterProcessMutex(cfClient, zkLockPath);
        try {
            boolean lockFlag = lock.acquire(20, TimeUnit.SECONDS);
            if (lockFlag) {
                System.out.println(Thread.currentThread().getName() + " hold lock.");
                //模拟业务逻辑
                System.out.println(Thread.currentThread().getName() + " start work ...");
                Thread.sleep(10_000);
                System.out.println(Thread.currentThread().getName() + " end work ...");
                System.out.println(Thread.currentThread().getName() + " release lock.");
            } else {
                System.out.println("can't get lock.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
