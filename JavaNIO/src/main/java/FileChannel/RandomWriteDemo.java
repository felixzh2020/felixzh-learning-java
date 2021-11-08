package FileChannel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class RandomWriteDemo {
    public static void main(String[] args) throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:/file1", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ExecutorService executorService = Executors.newFixedThreadPool(64);
        AtomicLong position = new AtomicLong(0);
        //并发写，达到模拟随机写的场景
        for (int i = 0; i < 1024; i++) {
            executorService.execute(() -> {
                try {
                    fileChannel.write(ByteBuffer.wrap(new byte[4 * 1024]), position.getAndAdd(4 * 1024));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
