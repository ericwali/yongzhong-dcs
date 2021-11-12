//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.zip;

import yozo.dcs.web.bo.ConvertParam;
import yozo.dcs.web.cons.DefaultResult;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.log.LogUtil;
import yozo.dcs.web.service.file.FileService;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipService {
    private static final String separator;

    static {
        separator = File.separator;
    }

    public ZipService() {
    }

    public static IResult<String> zipFile(ConvertParam param) {
        boolean zipIsOk = checkCopyfile(param.getOutput());
        if (!zipIsOk) {
            return DefaultResult.failResult("找不源文档");
        } else {
            File tmpDir = new File(Config.outputDir + separator + "temp" + separator + param.getTargetImageFolder());
            boolean isfolder = EnumConvertType.isImageTarget(param.getType());
            String zipFileName;
            String zipFilePath;
            if (isfolder) {
                FileService.copyDirectiory(param.getOutput(), tmpDir.getAbsolutePath());
            } else {
                zipFileName = param.getOutput();
                zipFilePath = zipFileName.substring(0, zipFileName.lastIndexOf(".")) + ".files";
                File targetFile = new File(zipFileName);
                File targetDir = new File(zipFilePath);
                FileService.copyFile(zipFileName, tmpDir.getAbsolutePath(), targetFile.getName());
                FileService.copyDirectiory(zipFilePath, tmpDir.getAbsolutePath() + separator + targetDir.getName());
            }

            zipFileName = param.getTargetImageFolder() + ".zip";
            zipFilePath = Config.outputDir + separator + zipFileName;

            DefaultResult var8;
            try {
                zip(zipFilePath, tmpDir);
                return DefaultResult.successResult(zipFileName);
            } catch (Exception var11) {
                LogUtil.error(var11);
                var8 = DefaultResult.failResult("压缩失败");
            } finally {
                FileService.deleteSource(tmpDir.getAbsolutePath());
            }

            return var8;
        }
    }

    private static boolean checkCopyfile(String... fileNames) {
        String[] var4 = fileNames;
        int var3 = fileNames.length;

        for(int var2 = 0; var2 < var3; ++var2) {
            String fileName = var4[var2];
            if (fileName == null || "".equals(fileName)) {
                return false;
            }

            if (!(new File(fileName)).exists()) {
                return false;
            }
        }

        return true;
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zip(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + separator));
            }

            for(int i = 0; i < fl.length; ++i) {
                zip(out, fl[i], base + separator + fl[i].getName(), bo);
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);

            int b;
            while((b = bi.read()) != -1) {
                bo.write(b);
            }

            bo.flush();
            bi.close();
            in.close();
        }

    }
}
