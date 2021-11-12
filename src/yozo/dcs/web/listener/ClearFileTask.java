//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.listener;

import yozo.dcs.web.log.LogUtil;
import yozo.dcs.web.service.file.FileService;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TimerTask;

public class ClearFileTask extends TimerTask {
    private Long clearTime;
    private Path clearPath;

    public ClearFileTask(String clearPath, Long clearTime) {
        this.clearPath = Paths.get(clearPath);
        this.clearTime = clearTime;
    }

    public void run() {
        final Long currentTime = System.currentTimeMillis();
        LogUtil.info("=======================================" + LogUtil.lineSep + "========================================" + LogUtil.lineSep + "开始清理过期文件" + LogUtil.lineSep + "========================================" + LogUtil.lineSep + "========================================" + LogUtil.lineSep);

        try {
            Files.walkFileTree(this.clearPath, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (currentTime - ClearFileTask.this.clearTime > file.toFile().lastModified()) {
                        FileService.deleteDir(file.toFile());
                    }

                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir.toFile().list().length == 0) {
                        dir.toFile().delete();
                        return FileVisitResult.SKIP_SUBTREE;
                    } else {
                        return FileVisitResult.CONTINUE;
                    }
                }
            });
        } catch (IOException var3) {
            LogUtil.info("清理过期文件线程异常：");
            LogUtil.error(var3);
        }

    }

    public static void main(String[] args) {
        System.out.println("================\n===============");
        Path path = Paths.get("E:\\dcs\\sampledoc\\output\\2017");
        System.out.println(path.toFile());
    }
}
