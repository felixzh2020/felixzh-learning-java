package c_apache_curator.distributed_barrier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DistributedDoubleBarrierCase {
    public static void main(String[] args) throws Exception {
        final String BASE_PATH = "/felixzh_barriers";
        final int runnerNum = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(runnerNum);
        for (int i = 0; i < runnerNum; i++) {
            int finalI = i;
            executorService.submit(() -> {
                CuratorFramework cfClient = CuratorFrameworkFactory.builder().connectString("felixzh:2181")
                        .retryPolicy(new ExponentialBackoffRetry(10_000, 3)).build();
                cfClient.start();
                try {
                    DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(cfClient, BASE_PATH, 2);
                    System.out.println("runner " + finalI + " ready.");
                    distributedDoubleBarrier.enter();
                    System.out.println("runner " + finalI + " finally.");
                    distributedDoubleBarrier.leave();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        Thread.sleep(3_000);
        executorService.shutdown();
    }
}
