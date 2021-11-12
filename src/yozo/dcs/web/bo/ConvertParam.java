//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.bo;

import sun.misc.BASE64Encoder;
import yozo.dcs.commons.util.DateViewUtils;
import yozo.dcs.web.cons.DefaultResult;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertParam {
    private String htmlName;
    private String encoding;
    private String htmlTitle;
    private Integer isCopy;
    private Integer isShowTitle;
    private Integer isDelSrc;
    private Integer isZip;
    private Integer isSourceDir;
    private Integer startPage = -1;
    private Integer endPage = -1;
    private Integer[] page;
    private Float zoom = 1.0F;
    private String[] mergeInput;
    private String[] bookMark;
    private String type;
    private String callbackName;
    private String wmContent;
    private Integer wmSize;
    private Color wmColor;
    private String wmFont;
    private String wmPicPath;
    private Integer isShowAll;
    private String password;
    private Integer isShowList;
    private String appendPath;
    private String destinationName;
    private Integer showFooter;
    private Integer isDelTarget;
    private String input;
    private String output;
    private String sourceFolder;
    private String relativePath;
    private String targetImageFolder;
    private String targetName;
    private String fileName;
    private Integer pageCount;

    public ConvertParam() {
    }

    public ConvertParam(String targetName, String fileName, String type, String input, String output) {
        this.targetName = targetName;
        this.fileName = fileName;
        this.type = type;
        this.input = input;
        this.output = output;
    }

    public IResult<ResultCode> checkParam(HttpServletRequest request) {
        boolean inputDir = this.input != null && !"".equals(this.input);
        boolean checkInput = inputDir ? (new File(this.input)).exists() : false;
        boolean checkOutput = this.output != null && !"".equals(this.output);
        boolean checkConvertType = EnumConvertType.getEnumByType(this.type) != null;
        if (checkInput && checkOutput && checkConvertType) {
            String appendPath = request.getParameter("appendPath");
            if (appendPath != null && !"".equals(appendPath)) {
                Pattern p = Pattern.compile("\\.\\./|\\.\\.\\\\");
                Matcher m = p.matcher(appendPath);
                Boolean bool = m.find();
                if (bool) {
                    return DefaultResult.failResult(ResultCode.E_INVALID_PARAM);
                }
            }

            String size = request.getParameter("size");

            long setsize;
            try {
                setsize = (long)Integer.valueOf(size);
            } catch (Exception var18) {
                setsize = 0L;
            }

            long finalsize;
            if (setsize > 0L) {
                finalsize = setsize * 1024L * 1024L;
            } else {
                finalsize = Config.uploadSize;
            }

            File sourceFile = new File(this.input);
            long filesize = sourceFile.length();
            if (filesize > finalsize) {
                return DefaultResult.failResult(ResultCode.E_UPLOAD_SIZE);
            } else {
                if (EnumConvertType.isMerge(this.type)) {
                    for(int i = 0; i < this.getMergeInput().length; ++i) {
                        File mergeFile = new File(this.getMergeInput()[i]);
                        if (mergeFile == null || !mergeFile.exists()) {
                            return DefaultResult.failResult(ResultCode.E_INPUT_FILE_NOTFOUND);
                        }
                    }
                }

                if (this.zoom <= 0.0F) {
                    this.zoom = 1.0F;
                }

                if (this.wmPicPath != null && !"".equals(this.wmPicPath)) {
                    try {
                        File wmPic = new File(this.wmPicPath);
                        if (!wmPic.exists()) {
                            return DefaultResult.failResult(ResultCode.E_INVALID_PARAM);
                        }

                        BufferedImage image = ImageIO.read(wmPic);
                        if (image == null) {
                            return DefaultResult.failResult(ResultCode.E_INVALID_PARAM);
                        }
                    } catch (Exception var17) {
                        return DefaultResult.failResult(ResultCode.E_INVALID_PARAM);
                    }
                }

                if (this.input.endsWith(".pdf")) {
                    if (!EnumConvertType.isIncludePdf(this.type)) {
                        return DefaultResult.failResult(ResultCode.E_INVALID_PARAM);
                    }
                } else if (this.input.endsWith(".ofd")) {
                    this.type = EnumConvertType.OFD_HTML_TEMP.getType();
                    this.setOutputByType(EnumConvertType.OFD_HTML_TEMP);
                } else if (!this.input.endsWith(".zip") && !this.input.endsWith(".rar")) {
                    if (this.input.endsWith(".jpg") || this.input.endsWith(".png")) {
                        this.type = EnumConvertType.PIC_HTML.getType();
                        this.setOutputByType(EnumConvertType.PIC_HTML);
                    }
                } else {
                    this.type = EnumConvertType.ZIP_HTML_TEMP.getType();
                    this.setOutputByType(EnumConvertType.ZIP_HTML_TEMP);
                }

                return DefaultResult.successResult();
            }
        } else {
            return !checkOutput ? DefaultResult.failResult(ResultCode.E_DESTINATIONNAME_ERROR) : DefaultResult.failResult(ResultCode.E_INVALID_PARAM);
        }
    }

    public void setOutputByType(EnumConvertType enumType) {
        if (enumType != null) {
            String output = Config.outputDir.getPath() + File.separator;
            String relative = "";
            if (this.appendPath != null && !"".equals(this.appendPath)) {
                relative = this.appendPath;
            } else if (Config.folderFormat != null && !"".equals(Config.folderFormat)) {
                relative = DateViewUtils.format(new Date(), Config.folderFormat).trim();
            }

            this.setType(enumType.getType());
            String tempName;
            if ("true".equals(Config.setOutFilename) && !"".equals(this.destinationName) && this.destinationName != null) {
                tempName = this.destinationName;
                tempName = this.checkdestinationName(tempName) ? tempName : null;
            } else {
                tempName = this.getName();
            }

            if (tempName != null) {
                String targetType = enumType.getExt();
                this.setTargetImageFolder(tempName);
                if (enumType.isImageType()) {
                    this.setTargetName(tempName);
                    if ("".equals(relative)) {
                        relative = tempName;
                    } else {
                        relative = relative + "/" + tempName;
                    }

                    output = Config.outputDir.getPath() + File.separator + relative;
                } else {
                    String tn = tempName + "." + targetType;
                    this.setTargetName(tn);
                    output = Config.outputDir.getPath() + File.separator + relative + File.separator + tn;
                }

                this.setRelativePath(relative.replaceAll("\\\\", "/"));
                this.setOutput(output);
            }
        }

    }

    private Boolean checkdestinationName(String filename) {
        Matcher matcher = Pattern.compile("[^/\\\\<>:*?|\"]+").matcher(filename);
        Boolean bool = matcher.matches();
        return bool;
    }

    private Boolean checkOutputName(String output) {
        File file = new File(output);
        return file.exists() ? false : true;
    }

    private String getName() {
        String date = DateViewUtils.format(new Date(), "yyMMdd");
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(6);
        String targetName = date + timestamp + (new Random()).nextInt(100);
        return (new BASE64Encoder()).encode(targetName.getBytes()).replaceAll("=", "");
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\.\\./|\\.\\.\\\\");
        Matcher m = p.matcher("///////\\\\\\\\\\\\\\||||||||......da..\\\\///...|||||");
        Boolean bool = m.find();
        if (bool) {
            System.out.println("失败");
        }
    }

    public String[] getBookMark() {
        return this.bookMark;
    }

    public void setBookMark(String[] bookMark) {
        this.bookMark = bookMark;
    }

    public String[] getMergeInput() {
        return this.mergeInput;
    }

    public void setMergeInput(String[] mergeInput1) {
        this.mergeInput = mergeInput1;
    }

    public Integer getIsCopy() {
        return this.isCopy;
    }

    public void setIsCopy(Integer isCopy) {
        this.isCopy = isCopy;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getHtmlTitle() {
        return this.htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getHtmlName() {
        return this.htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStartPage() {
        return this.startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return this.endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Float getZoom() {
        return this.zoom;
    }

    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }

    public String getTargetImageFolder() {
        return this.targetImageFolder;
    }

    public void setTargetImageFolder(String targetImageFolder) {
        this.targetImageFolder = targetImageFolder;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return this.output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getCallbackName() {
        return this.callbackName;
    }

    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }

    public String getSourceFolder() {
        return this.sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public Integer getIsShowTitle() {
        return this.isShowTitle;
    }

    public void setIsShowTitle(Integer isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    public Integer getIsDelSrc() {
        return this.isDelSrc;
    }

    public void setIsDelSrc(Integer isDelSrc) {
        this.isDelSrc = isDelSrc;
    }

    public Integer getIsZip() {
        return this.isZip;
    }

    public void setIsZip(Integer isZip) {
        this.isZip = isZip;
    }

    public Integer[] getPage() {
        return this.page;
    }

    public void setPage(Integer[] page) {
        this.page = page;
    }

    public Integer getIsSourceDir() {
        return this.isSourceDir;
    }

    public void setIsSourceDir(Integer isSourceDir) {
        this.isSourceDir = isSourceDir;
    }

    public String getWmContent() {
        return this.wmContent;
    }

    public void setWmContent(String wmContent) {
        this.wmContent = wmContent;
    }

    public Integer getWmSize() {
        return this.wmSize;
    }

    public void setWmSize(Integer wmSize) {
        this.wmSize = wmSize;
    }

    public String getWmFont() {
        return this.wmFont;
    }

    public void setWmFont(String wmFont) {
        this.wmFont = wmFont;
    }

    public Color getWmColor() {
        return this.wmColor;
    }

    public void setWmColor(Color wmColor) {
        this.wmColor = wmColor;
    }

    public String getWmPicPath() {
        return this.wmPicPath;
    }

    public void setWmPicPath(String wmPicPath) {
        this.wmPicPath = wmPicPath;
    }

    public Integer getIsShowAll() {
        return this.isShowAll;
    }

    public void setIsShowAll(Integer isShowAll) {
        this.isShowAll = isShowAll;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsShowList() {
        return this.isShowList;
    }

    public void setIsShowList(Integer isShowList) {
        this.isShowList = isShowList;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAppendPath() {
        return this.appendPath;
    }

    public void setAppendPath(String appendPath) {
        this.appendPath = appendPath;
    }

    public String getDestinationName() {
        return this.destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Integer getShowFooter() {
        return this.showFooter;
    }

    public void setShowFooter(Integer showFooter) {
        this.showFooter = showFooter;
    }

    public Integer getIsDelTarget() {
        return this.isDelTarget;
    }

    public void setIsDelTarget(Integer isDelTarget) {
        this.isDelTarget = isDelTarget;
    }
}
