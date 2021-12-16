package c_apache_curator.distributed_count;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedCountCase {
    public static void main(String[] args) throws Exception {
        final int clientNum = 5;
        final String BASE_PATH = "/felixzh/counter";

        CuratorFramework cfClient = CuratorFrameworkFactory.builder().connectString("felixzh:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        cfClient.start();


        ExecutorService executorService = Executors.newFixedThreadPool(clientNum);
        for (int i = 0; i < clientNum; i++) {


            executorService.submit(() -> {
                try {
                    SharedCount sharedCount = new SharedCount(cfClient, BASE_PATH, 0);
                    sharedCount.addListener(new SharedCountListener() {
                        @Override
                        public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
                            //每个线程都能监听到变化
                            //System.out.println(sharedCount.getVersionedValue().getValue() + "," + newCount);
                        }

                        @Override
                        public void stateChanged(CuratorFramework client, ConnectionState newState) {

                        }
                    });
                    sharedCount.start();
                    boolean res = false;

                    while (!res) {
                        res = sharedCount.trySetCount(sharedCount.getVersionedValue(), sharedCount.getVersionedValue().getValue() + 1);
                    }

                    System.out.println("current value: " + sharedCount.getVersionedValue().getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3_000);
        executorService.shutdown();
    }
}
