package c_apache_curator.distributed_count;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class DistributedAtomicLongCase {
    public static void main(String[] args) throws Exception {
        CuratorFramework cfClient = CuratorFrameworkFactory.builder().connectString("felixzh:2181")
                .retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
        cfClient.start();

        final int clientNum = 5;
        final String BASE_PATH = "/felixzh_distributed_count";

        ExecutorService executorService = Executors.newFixedThreadPool(clientNum);
        for (int i = 0; i < clientNum; i++) {

            executorService.submit(() -> {
                try {
                    final DistributedAtomicLong distributedAtomicLong = new DistributedAtomicLong(cfClient, BASE_PATH, new RetryNTimes(3, 1000));
                    AtomicValue<Long> atomicValue = distributedAtomicLong.increment();
                    if (atomicValue.succeeded()) {
                        System.out.println("pre value: " + atomicValue.preValue() + "," + "post value: " + atomicValue.postValue());
                        System.out.println("current value: " + distributedAtomicLong.get().postValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3_000);
        executorService.shutdown();
    }
}
