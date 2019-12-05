package ReentrantReadWriteLock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest2 {

    public static void main(String[] args) {
        Data readWriteData = new Data();
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> readWriteData.writeData(String.valueOf(new Random().nextInt(1000))));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            executorService.submit(() -> readWriteData.readData());
        }
    }
}

class Data {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String data;

    void writeData(String data) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("start write data : " + data);
            this.data = data;
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        readWriteLock.writeLock().unlock();
    }

    void readData() {
        readWriteLock.readLock().lock();
        try {
            System.out.println("start read data : " + this.data);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        readWriteLock.readLock().unlock();
    }

}
