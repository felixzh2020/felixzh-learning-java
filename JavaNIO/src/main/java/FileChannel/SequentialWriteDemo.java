package FileChannel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class SequentialWriteDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:/file2", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ExecutorService executorService = Executors.newFixedThreadPool(64);
        AtomicLong position = new AtomicLong(0);

        //加锁保证顺序写
        for (int i = 0; i < 1024; i++) {
            executorService.execute(() -> {
                try {
                    write(new byte[4 * 1024], fileChannel, position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static synchronized void write(byte[] data, FileChannel fileChannel, AtomicLong position) throws Exception {
        fileChannel.write(ByteBuffer.wrap(data), position.getAndAdd(4 * 1024));
    }
}
