package c_apache_curator.distributed_barrier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DistributedBarrierCase {
    public static DistributedBarrier distributedBarrier = null;

    public static void main(String[] args) throws Exception {
        final String BASE_PATH = "/felixzh_barrier";
        final int runnerNum = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(runnerNum);
        for (int i = 0; i < runnerNum; i++) {
            int finalI = i;
            executorService.submit(() -> {
                CuratorFramework cfClient = CuratorFrameworkFactory.builder().connectString("felixzh:2181")
                        .retryPolicy(new ExponentialBackoffRetry(10_000, 3)).build();
                cfClient.start();
                distributedBarrier = new DistributedBarrier(cfClient, BASE_PATH);

                try {
                    System.out.println("runner " + finalI + " ready.");
                    distributedBarrier.setBarrier();
                    distributedBarrier.waitOnBarrier();
                    System.out.println("runner " + finalI + " start run.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(3_000);

        if (distributedBarrier != null) {
            distributedBarrier.removeBarrier();
        }

        executorService.shutdown();
    }
}
