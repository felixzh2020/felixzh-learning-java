package com.felixzh.NIO_FileChannel;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * readBigFileAsString 适合大文件
 * <p>
 * readSmallFileAsString 适合小文件
 */
public class MappedByteBuffer_KafkaUtils {

    public static void main(String[] args) throws Exception {
        String path = "c://felixzh";

        System.out.println(readBigFileAsString(path, StandardCharsets.UTF_8));

        System.out.println(readSmallFileAsString(path));

    }

    /**
     * Attempt to read a file as a string
     * Maps a region of this channel's file directly into memory
     * A mapping, once established, is not dependent upon the file channel
     * that was used to create it.  Closing the channel, in particular, has no effect upon the validity of the mapping
     * <p>
     * For most operating systems, mapping a file into memory is more
     * expensive than reading or writing a few tens of kilobytes of data via
     * the usual read and write methods.
     * <p>
     * From the standpoint of performance it is generally only worth mapping relatively large files into memory
     */
    private static String readBigFileAsString(String path, Charset charset) throws IOException {
        if (charset == null) charset = Charset.defaultCharset();

        try (FileChannel fc = FileChannel.open(Paths.get(path))) {
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return charset.decode(bb).toString();
        }
    }

    /**
     * Read a file as string and return the content. The file is treated as a stream and no seek is performed.
     * This allows the program to read from a regular file as well as from a pipe/fifo.
     * <p>
     * Note that this method is intended for simple cases where it is convenient to read all bytes into a byte array.
     * It is not intended for reading in large files.
     */
    public static String readSmallFileAsString(String path) throws IOException {
        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(path));
            return new String(allBytes, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IOException("Unable to read file " + path, ex);
        }
    }

}
