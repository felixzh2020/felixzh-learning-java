package com.felixzh.NIO_FileChannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

public class transferTo {

    public static void main(String[] args) throws Exception {
        FileChannel in2 = new FileInputStream("D:\\Download\\HDP-3.0.0.0-centos7-rpm.tar.gz").getChannel();
        FileChannel out2 = new FileOutputStream("D:\\Download\\HDP-3.0.0.0-centos7-rpm-transferTo.tar.gz").getChannel();

        System.out.println(new Date());

        System.out.println("position : " + in2.position());
        System.out.println("size : " + in2.size());

        long position = 0;
        long leftSize = in2.size();

        while (leftSize > 0) {
            long count = in2.transferTo(position, leftSize, out2);

            position += count;
            leftSize -= count;

            System.out.println(count / 1024);
            System.out.println("position : " + position);
            System.out.println("size : " + leftSize);
        }

        System.out.println(new Date());

        out2.close();
        in2.close();


    }

}
