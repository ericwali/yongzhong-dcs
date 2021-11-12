//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.upload;

import java.io.InputStream;

public class FileUploadInfo {
    private String fileName;
    private long fileSize;
    private InputStream inputStream;

    public FileUploadInfo(String fileName, long fileSize, InputStream inputStream) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return this.fileName;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }
}
