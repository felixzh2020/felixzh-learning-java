package FileChannel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * A Java NIO FileChannel is a channel that is connected to a file.
 * Using a file channel you can read data from a file, and write data to a file.
 * The Java NIO FileChannel class is NIO's an alternative to reading files with the standard Java IO API.
 * <p>
 * A FileChannel cannot be set into non-blocking mode. It always runs in blocking mode.
 *
 * http://tutorials.jenkov.com/java-nio/file-channel.html
 */
public class FileChannelDemo {
    public static void main(String[] args) throws Exception {
        /**
         * Before you can use a FileChannel you must open it.
         * You cannot open a FileChannel directly.
         * You need to obtain a FileChannel via an InputStream, OutputStream, or a RandomAccessFile.
         *
         * Here is how you open a FileChannel via a RandomAccessFile:
         * */
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:/file0", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * Writing data to a FileChannel is done using the FileChannel.write() method, which takes a Buffer as parameter.
         * Here is an example:
         * */
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        /**
         * Notice how the FileChannel.write() method is called inside a while-loop.
         * There is no guarantee of how many bytes the write() method writes to the FileChannel.
         * Therefore we repeat the write() call until the Buffer has no further bytes to write.
         * */
        while (buf.hasRemaining()) {
            fileChannel.write(buf);
        }
        fileChannel.close();
        randomAccessFile.close();
    }
}
