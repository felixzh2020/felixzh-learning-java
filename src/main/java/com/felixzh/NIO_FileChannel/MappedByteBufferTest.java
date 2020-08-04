package com.felixzh.NIO_FileChannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 内存映射文件
 * <p>
 * 这个可谓 “大杀器”,特别是大文件读写时,效率比普通IO要快N倍。
 * 据一位网友测试86M的文件进行读操作,内存映射方式只要78ms,而普通IO需要468ms,差了6倍。可见威力无穷。
 * <p>
 * 为什么这么快?
 * 普通IO是操作系统先读入到内核缓冲器,再转到用户进程私有的内存区,当然JVM进程还作了内核态和用户态的切换;
 * 而内存映射方式,是将文件直接映射到内存中的一块区域,当调用读时会抛出缺页异常,OS会读取该页数据,依次往复。
 * 因此这里少了依次内核缓冲区到私有内存之间的拷贝,所以快了不少。
 * <p>
 * 内存映射模式：
 * read_only只读设置 ；
 * read_write读写都可，并且任何写操作立即反应在文件上,其他共享该文件的内存映射也能立即看到 。
 * private私有模式，不写时跟read_only 一样，但是写时会克隆一个独立内存区域，不会影响文件。
 */
public class MappedByteBufferTest {

    public static void main(String[] args) {
        String path = "c:/felixzh";
        write(path);

        try {
            //Path paths = Paths.get(path);
//            FileChannel channel = FileChannel.open(paths);//HEX{01 02 03 04}
            FileInputStream in = new FileInputStream(path);
            FileChannel channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

            while (buffer.hasRemaining()) {
                char ch = (char) buffer.get();
                System.out.print(ch);
            }

            buffer.flip();
            channel.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(String path) {
        try {
            FileOutputStream out = new FileOutputStream(path);
            FileChannel channel = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(20);

            buffer.put((byte) 0x1B);
            buffer.put((byte) 0x2B);
            buffer.put((byte) 0x3B);
            buffer.put((byte) 0x4B);
            buffer.put((byte) 0x5B);
            buffer.put((byte) 0xFB);
            buffer.put((byte) 0xEB);

            buffer.flip();

            channel.write(buffer, 0);
            channel.close();

            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
