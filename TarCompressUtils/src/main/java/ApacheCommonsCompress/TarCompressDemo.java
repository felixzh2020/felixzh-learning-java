package ApacheCommonsCompress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.*;
import java.util.Objects;

public class TarCompressDemo {

    public static void main(String[] args) {
        String sourceDir = "D:\\downloads\\out1\\kafka";
        String targetFile = "D:\\downloads\\out1\\target.tar.gz";

        System.out.println(compressTar(sourceDir, targetFile, ""));
    }

    private static boolean compressTar(String sourceDir, String targetFile, String base) {
        TarArchiveOutputStream tarArchiveOutputStream = null;
        try {
            tarArchiveOutputStream = new TarArchiveOutputStream(new FileOutputStream(targetFile));
            addFileToTar(sourceDir, tarArchiveOutputStream, "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (tarArchiveOutputStream != null) {
                    tarArchiveOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void addFileToTar(String sourceDir, TarArchiveOutputStream tarOutput, String base) throws Exception {
        File file = new File(sourceDir);
        String entryName = base + file.getName();
        TarArchiveEntry entry = new TarArchiveEntry(file, entryName);
        entry.setSize(file.length());
        tarOutput.putArchiveEntry(entry);
        if (file.isFile()) {
            try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = input.read(buffer)) != -1) {
                    tarOutput.write(buffer, 0, read);
                }
                tarOutput.closeArchiveEntry();
            }
        } else if (file.isDirectory()) {
            tarOutput.closeArchiveEntry();
            for (File child : Objects.requireNonNull(file.listFiles())) {
                addFileToTar(child.getAbsolutePath(), tarOutput, entryName + "/");
            }
        }
    }
}
