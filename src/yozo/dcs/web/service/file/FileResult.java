//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.file;

public class FileResult {
    private int size;
    private String filePath;
    private String imagePath;

    public FileResult() {
    }

    public FileResult(int size, String filePath, String imagePath) {
        this.size = size;
        this.filePath = filePath;
        this.imagePath = imagePath;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
