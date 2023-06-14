package ApacheCommonsCompress;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

public class DecompressionUtil {
    private static String archive(String path) throws Exception {
        File file = new File(path);

        FileOutputStream fos = null;
        TarArchiveOutputStream tos = null;
        String baseName = file.getName();
        String tarName = file.getAbsolutePath() + ".tar";
        try {
            fos = new FileOutputStream(tarName);
            tos = new TarArchiveOutputStream(fos);

            if (file.isDirectory()) {
                archiveDir(file, tos, baseName);
            } else {
                archiveHandle(tos, file, baseName);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                tos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tarName;
    }

    /**
     * 递归处理，准备好路径
     */
    private static void archiveDir(File file, TarArchiveOutputStream tos, String basePath) throws Exception {
        File[] listFiles = file.listFiles();
        for (File fi : listFiles) {
            if (fi.isDirectory()) {
                archiveDir(fi, tos, basePath + File.separator + fi.getName());
            } else {
                archiveHandle(tos, fi, basePath);
            }
        }
    }

    /**
     * 具体归档处理（文件）
     */
    private static void archiveHandle(TarArchiveOutputStream tos, File fi, String basePath) throws Exception {
        TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());
        tEntry.setSize(fi.length());

        tos.putArchiveEntry(tEntry);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(fi);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = bis.read(buffer)) != -1) {
                tos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tos.closeArchiveEntry();//这里必须写，否则会失败
    }

    /**
     * 把tar包压缩成gz
     */
    public static String compressArchive(String path) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));

        GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(path + ".gz")));

        byte[] buffer = new byte[1024];
        int read = -1;
        while ((read = bis.read(buffer)) != -1) {
            gcos.write(buffer, 0, read);
        }
        gcos.close();
        bis.close();
        return path + ".gz";
    }

    /**
     * 解压成tar
     */
    public static Boolean unCompressTargz(String gzPath) throws Exception {
        Boolean flag = true;

        File file = new File(gzPath);

        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        String finalName = file.getParent() + File.separator + fileName;

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        GzipCompressorInputStream gcis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            fos = new FileOutputStream(finalName);
            bos = new BufferedOutputStream(fos);
            gcis = new GzipCompressorInputStream(bis);
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = gcis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            flag = false;
            //throw e;
        } finally {
            try {
                gcis.close();
                bos.close();
                fos.close();
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag) flag = unCompressTar(finalName);
        file = new File(finalName);
        file.delete();//删除tar文件
        return flag;
    }

    /**
     * 解压tar
     */
    private static Boolean unCompressTar(String finalName) throws Exception {
        Boolean flag = true;
        File file = new File(finalName);
        String parentPath = file.getParent();

        FileInputStream fis = null;
        TarArchiveInputStream tais = null;
        try {
            fis = new FileInputStream(file);
            tais = new TarArchiveInputStream(fis);
            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File tarFile = new File(parentPath, name);
                if (!tarFile.getParentFile().exists()) {
                    tarFile.getParentFile().mkdirs();
                }

                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                try {
                    fos = new FileOutputStream(tarFile);
                    bos = new BufferedOutputStream(fos);
                    int read = -1;
                    byte[] buffer = new byte[1024];
                    while ((read = tais.read(buffer)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                } catch (Exception e) {
                    throw e;
                } finally {
                    bos.close();
                    fos.close();
                }
            }
        } catch (Exception e) {
            flag = false;
            throw e;
        } finally {
            try {
                fis.close();
                tais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return flag;
    }

    public static String compressToTargz(String path) throws Exception {
        //生成tar包
        String tarPath = archive(path);
        String gzPath = compressArchive(tarPath);//生成gz包
        File file = new File(tarPath);
        file.delete();//删除tar文件
        return gzPath;
    }

    public static void main(String[] args) {
        String path = "D:\\downloads\\out1\\kafka";//需要压缩的文件夹
        try {
            //压缩
            System.out.println(compressToTargz(path));

            //解压
            //System.out.println(unCompressTargz("D:\\test\\compress.tar.gz"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
