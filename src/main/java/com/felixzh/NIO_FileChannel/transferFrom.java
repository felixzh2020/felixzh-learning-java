package com.felixzh.NIO_FileChannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

public class transferFrom {

    public static void main(String[] args) throws Exception {
        FileChannel in1 = new FileInputStream("D:\\Download\\HDP-3.0.0.0-centos7-rpm.tar.gz").getChannel();
        FileChannel out1 = new FileOutputStream("D:\\Download\\HDP-3.0.0.0-centos7-rpm-transFrom.tar.gz").getChannel();

        System.out.println(new Date());
        out1.transferFrom(in1, 0, in1.size());

        System.out.println(new Date());
        out1.close();
        in1.close();
    }

}
