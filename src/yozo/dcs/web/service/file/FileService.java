//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.file;

import org.apache.commons.io.IOUtils;
import yozo.dcs.web.log.LogUtil;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public static final String HTML5_FILE_TYPE = "application/octet-stream";

    public FileService() {
    }

    public static void copyFile(File sourcefile, File targetFile) {
        if (targetFile.exists()) {
            targetFile.delete();
        }

        try {
            Files.copy(sourcefile.toPath(), targetFile.toPath());
        } catch (IOException var3) {
            LogUtil.error(var3);
            var3.printStackTrace();
        }

    }

    public static void copyDirectiory(String sourceDir, String targetDir) {
        if ((new File(sourceDir)).isDirectory()) {
            (new File(targetDir)).mkdirs();
            File[] file = (new File(sourceDir)).listFiles();

            for(int i = 0; i < file.length; ++i) {
                if (file[i].isFile()) {
                    File sourceFile = file[i];
                    File targetFile = new File((new File(targetDir)).getAbsolutePath() + File.separator + file[i].getName());
                    copyFile(sourceFile, targetFile);
                }

                if (file[i].isDirectory()) {
                    String dir1 = sourceDir + File.separator + file[i].getName();
                    String dir2 = targetDir + File.separator + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }

        }
    }

    public static boolean copyFile(String src, String to, String targetName) {
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(src);
            File dir = new File(to);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File outFile = new File(to + File.separator + targetName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            outputStream = new FileOutputStream(outFile);
            IOUtils.copy(inputStream, outputStream);
            return true;
        } catch (IOException var11) {
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

        return false;
    }

    public static boolean inputStreamToFile(InputStream inputStream, String savePath) {
        if (inputStream == null) {
            return false;
        } else {
            int index = savePath.lastIndexOf(File.separator);
            String dirString = savePath.substring(0, index);
            FileOutputStream out = null;

            try {
                File dir = new File(dirString);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File outFile = new File(savePath);
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }

                out = new FileOutputStream(outFile);
                IOUtils.copy(inputStream, out);
                return true;
            } catch (EOFException var11) {
                if ("Unexpected end of ZLIB input stream".equals(var11.getMessage())) {
                    return true;
                }

                return false;
            } catch (Exception var12) {
                var12.printStackTrace();
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(inputStream);
            }

            return false;
        }
    }

    public static boolean inputStreamCopyToFile(InputStream inputStream, String savePath) {
        if (inputStream == null) {
            return false;
        } else {
            try {
                int index = savePath.lastIndexOf(File.separator);
                String dirString = savePath.substring(0, index);
                Path copyDir = Paths.get(dirString);
                if (!Files.exists(copyDir, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                    Files.createDirectory(copyDir);
                }

                Path copyPath = Paths.get(savePath);
                boolean var7 = Files.copy(inputStream, copyPath, new CopyOption[0]) >= 0L;
                return var7;
            } catch (EOFException var11) {
                if ("Unexpected end of ZLIB input stream".equals(var11.getMessage())) {
                    return true;
                }
            } catch (Exception var12) {
                var12.printStackTrace();
                LogUtil.error(var12);
                return false;
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            return false;
        }
    }

    public static List<String> getFileName(String filePath) {
        List<String> fileNames = new ArrayList();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            String[] files = file.list();
            String[] var7 = files;
            int var6 = files.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                String name = var7[var5];
                File temp = new File(filePath + File.separator + name);
                if (!temp.isDirectory()) {
                    fileNames.add(temp.getName());
                }
            }

            return fileNames;
        } else {
            return fileNames;
        }
    }

    public static void deleteSource(String input) {
        if (input != null && !"".equals(input)) {
            File dir = new File(input);
            if (dir.exists()) {
                if (dir.isDirectory()) {
                    deleteDir(dir);
                } else {
                    deleteDir(dir.getParentFile());
                }
            }

        }
    }

    public static void deleteTarget(String output) {
        if (output != null && !"".equals(output)) {
            File out = new File(output);
            if (out.exists()) {
                if (out.isDirectory()) {
                    deleteDir(out);
                } else {
                    deleteDir(out);
                    int fileindex = output.lastIndexOf(".");
                    if (fileindex != -1) {
                        String outfile = output.substring(0, fileindex) + ".files";
                        File outFile = new File(outfile);
                        if (outFile.exists()) {
                            deleteDir(outFile);
                        }
                    }
                }
            }

        }
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();

            for(int i = 0; i < children.length; ++i) {
                deleteDir(new File(dir, children[i]));
            }
        }

        dir.delete();
    }
}
