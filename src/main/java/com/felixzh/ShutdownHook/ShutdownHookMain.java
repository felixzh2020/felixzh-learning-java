package com.felixzh.ShutdownHook;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author felixzh
 *
 *  如何优雅的关闭线程池
 *
 * isShutDown当调用shutdown()或shutdownNow()方法后返回为true。 
 * isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
 * isTerminated当调用shutdownNow()方法后，成功停止后返回为true;
 * 如果线程池任务正常完成，都为false
 *
 * */

public class ShutdownHookMain {

    public static void main(String [] args) throws Exception{
        String threadName = "ShutdownHookTest";
        ExecutorService threadPool = Executors.newFixedThreadPool(10, new TestThreadFactory(threadName));

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Inside Add Shutdown Hook");
                shutdownGracefully(threadPool, threadName);
            }
        });
        System.out.println("Shut Down Hook Attached.");

        threadPool.submit(new ShortTask());
        threadPool.submit(new LongTask());

        Thread.sleep(1000 * 10);
        System.exit(0);
    }

    public static void shutdownGracefully(ExecutorService threadPool, String alias){
        shutdownThreadPool(threadPool, alias);
    }

    public static void shutdownThreadPool(ExecutorService threadPool, String alias){
        System.out.println("start to shutdown the thread pool:" + alias );
        //新任务不再接受提交
        // Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted
        threadPool.shutdown();
        try{
            //等待未完成任务结束
            // Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs,
            // or the current thread is interrupted, whichever happens first.
            if(!threadPool.awaitTermination(60, TimeUnit.SECONDS)){
                System.out.println("threadPool.isTerminated(): " + threadPool.isTerminated());
                System.out.println("threadPool.isShutdown(): " + threadPool.isShutdown());
                //取消当前执行的任务
                //Attempts to stop all actively executing tasks, halts the processing of waiting tasks,
                // and returns a list of the tasks that were awaiting execution.
                threadPool.shutdownNow();
                System.out.println("WARN: Interrupt the worker, which may cause some task inconsistent. Please check the biz logs.");

                //等待任务取消的响应
                if(!threadPool.awaitTermination(60, TimeUnit.SECONDS)){
                    System.out.println("ERROR: Thread pool can't be shutdown even with interrupting worker threads, " +
                            "which may cause some task inconsistent. Please check the biz logs.");
                }
            }
        }catch (InterruptedException ie){
            //重新取消当前线程进行中断
            threadPool.shutdownNow();
            System.out.println("ERROR: The current server thread is interrupted when it is trying to stop the worker threads." +
                    "This may leave an inconsistent state. Please check the biz logs.");

            //保留中断状态
            Thread.currentThread().interrupt();
        }

        System.out.println("Finally shutdown the thread pool: " + alias);
    }

}
