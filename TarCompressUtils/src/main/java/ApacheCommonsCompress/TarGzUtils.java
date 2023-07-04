package ApacheCommonsCompress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.GZIPOutputStream;

/**
 * JavaAPI基于org.apache.commons:commons-compress实现tar.gz压缩与解压
 *
 * @author FelixZh
 */
public class TarGzUtils {
    public static void compress(String sourceDirPath, String targetFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(targetFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        TarArchiveOutputStream tos = new TarArchiveOutputStream(new GZIPOutputStream(bos));
        Path sourceDir = Paths.get(sourceDirPath);
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                TarArchiveEntry entry = new TarArchiveEntry(file.toFile(), sourceDir.relativize(file).toString());
                tos.putArchiveEntry(entry);
                IOUtils.copy(new FileInputStream(file.toFile()), tos);
                tos.closeArchiveEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        tos.close();
        bos.close();
        fos.close();
    }

    public static void deCompress(String sourceFilePath, String targetDirPath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        BufferedInputStream bis = new BufferedInputStream(fis);
        GzipCompressorInputStream gis = new GzipCompressorInputStream(bis);
        TarArchiveInputStream tis = new TarArchiveInputStream(gis);
        TarArchiveEntry entry;
        while ((entry = tis.getNextTarEntry()) != null) {
            if (entry.isDirectory()) {
                Files.createDirectories(Paths.get(targetDirPath, entry.getName()));
                continue;
            }
            Path targetPath = Paths.get(targetDirPath, entry.getName());
            Files.createDirectories(targetPath.getParent());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(tis, baos);
            Files.write(targetPath, baos.toByteArray());
        }
        tis.close();
        gis.close();
        bis.close();
        fis.close();
    }

    public static void main(String[] args) throws Exception {
        compress("d:/opt/target", "d:/opt/target.tar.gz");
        deCompress("d:/opt/target.tar.gz", "d:/opt/target1");
    }
}
