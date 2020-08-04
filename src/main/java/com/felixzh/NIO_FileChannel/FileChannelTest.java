package com.felixzh.NIO_FileChannel;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        //Path path = Paths.get("c://felixzh");
        Path path = new File("c://felixzh").toPath();

        //覆盖写：文件不存在，新建
        //FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);

        //不支持文件已存在
        //FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, StandardOpenOption.READ);

        //追加写：Exception in thread "main" java.lang.IllegalArgumentException: READ + APPEND not allowed
        //FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.APPEND);

        //追加写
        //FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);

        //读取
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);


        //fileChannel.lock();
        //fileChannel.force(true);
        //fileChannel.transferTo();
        //fileChannel.transferFrom();

        Date date = new Date();
        System.out.println(date);

        byte[] data = ("this is a test! " + date).getBytes();
        System.out.println("data.length : " + data.length);

        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);

        //byteBuffer.mark();
        //byteBuffer.reset();

        //byteBuffer.position(10);

        byteBuffer.clear();
        printMsg(byteBuffer);

        byteBuffer.put(data);
        printMsg(byteBuffer);

        byteBuffer.flip();
        printMsg(byteBuffer);

        while (byteBuffer.hasRemaining()) {
            fileChannel.write(byteBuffer);
        }

        printMsg(byteBuffer);

        byteBuffer.clear();

        while (true) {
            int count = fileChannel.read(byteBuffer);
            if (count <= -1) {
                break;
            }
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                char ch = (char) byteBuffer.get();
                System.out.print(ch);
            }
            byteBuffer.compact();
        }

        fileChannel.close();

    }

    private static void printMsg(ByteBuffer byteBuffer) {
        System.out.println("---------------------start------------------------------------");
        System.out.println("byteBuffer.limit : " + byteBuffer.limit());
        System.out.println("byteBuffer.position : " + byteBuffer.position());
        System.out.println("byteBuffer.hasRemaining : " + byteBuffer.hasRemaining());
        System.out.println("----------------------end-----------------------------------");
    }
}
