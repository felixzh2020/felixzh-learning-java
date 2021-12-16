package c_apache_curator.distributed_barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author FelixZh
 */
public class JDKCyclicBarrier {
    public static void main(String[] args) throws Exception {
        final int runnerNum = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(runnerNum);
        ExecutorService executorService = Executors.newFixedThreadPool(runnerNum);
        for (int i = 1; i <= runnerNum; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    System.out.println("runner " + finalI + " ready.");
                    cyclicBarrier.await();
                    System.out.println("runner " + finalI + " start run.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        Thread.sleep(3_000);
        executorService.shutdown();
    }
}
