//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.convert;

import application.dcs.Convert;
import application.dcs.IHtmlConvertor;
import application.dcs.IPICConvertor;
import yozo.dcs.web.bo.ConvertParam;
import yozo.dcs.web.cons.DefaultResult;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.log.LogUtil;
import yozo.dcs.web.service.file.FileService;

import java.io.File;

public class ConvertInstance {
    private Convert converter = new Convert();

    public ConvertInstance(Integer index) {
        this.converter.setEmptyCount(50, 50);
        this.converter.setXlsxMaxRowCol(65536, 256);
        if (Config.tempDir != null && !"".equals(Config.tempDir)) {
            this.converter.setTempPath(Config.tempDir + File.separator + index + File.separator);
        }

    }

    public static void main(String[] args) {
        String a = "C:\\Users\\LENOVO\\Desktop\\test.pdf";
        String b = "C:\\Users\\LENOVO\\Desktop\\test\\test.html";
        Convert converter=new Convert();
        converter.convertMStoHtmlOfSvg(a, b);
    }

    public int queryTimes() {
        return this.converter.getTimes();
    }

    public int queryDays() {
        return this.converter.getDays();
    }

    public void convertdoc(String inFile, String outFile, ConvertParam param) {
        if (param.getBookMark() != null && param.getBookMark().length > 0) {
            this.converter.setBookmarks(param.getBookMark());
        }

        this.converter.convert(inFile, outFile);
    }

    public void clearParam() {
        this.converter.setOpenPsw((String)null);
        this.converter.setViewAll(false);
        this.converter.setAntiCopy(false);
        this.converter.setShowList(true);
        this.converter.setShowTitle(true);
        this.converter.setHtmlTitle("永中文档转换服务");
        this.converter.setBookmarks((String[])null);
    }

    public IResult<Integer> convert(ConvertParam param) {
        this.clearParam();
        this.converter.setTimeout(Config.convertTimeout);
        if (param.getWmContent() != null && !"".equals(param.getWmContent())) {
            this.converter.insertTextWaterMark(param.getWmContent(), param.getWmFont(), param.getWmSize(), param.getWmColor(), true, true, true);
        }

        if (Config.AcceptTracks != null && "true".equals(Config.AcceptTracks)) {
            this.converter.setAcceptTracks(true);
        }

        if (param.getPassword() != null && !"".equals(param.getPassword())) {
            this.converter.setOpenPsw(param.getPassword());
        }

        if (Config.filePermission != null && !"".equals(Config.filePermission) && Config.filePermission.equals("true")) {
            this.converter.setFilePermission(true);
        } else {
            this.converter.setFilePermission(false);
        }

        if (param.getShowFooter() != null && param.getShowFooter() == 0) {
            this.converter.setShowFooter(false);
        } else {
            this.converter.setShowFooter(true);
        }

        if (param.getIsShowAll() != null && param.getIsShowAll() == 1) {
            this.converter.setViewAll(true);
        }

        if (param.getWmPicPath() != null && !"".equals(param.getWmPicPath())) {
            this.converter.insertPicWaterMark(param.getWmPicPath(), 1, false, true);
        }

        if (param.getIsCopy() != null && param.getIsCopy() == 1) {
            this.converter.setAntiCopy(true);
        }

        if (param.getIsShowList() != null && param.getIsShowList() == 0) {
            this.converter.setShowList(false);
        }

        if (param.getHtmlName() != null && !"".equals(param.getHtmlName())) {
            this.converter.setHtmlName(param.getHtmlName());
        } else {
            this.converter.setHtmlName(param.getFileName());
        }

        if (param.getIsShowTitle() != null && param.getIsShowTitle() == 0) {
            this.converter.setShowTitle(false);
        }

        if (param.getEncoding() != null && !"".equals(param.getEncoding())) {
            this.converter.setHtmlEncoding(param.getEncoding());
        }

        if (param.getHtmlTitle() != null && !"".equals(param.getHtmlTitle())) {
            this.converter.setHtmlTitle(param.getHtmlTitle());
        } else if (Config.htmlTitle != null && !"".equals(Config.htmlTitle.trim())) {
            if ("$name".equals(Config.htmlTitle)) {
                if (param.getHtmlName() != null && !"".equals(param.getHtmlName())) {
                    this.converter.setHtmlTitle(param.getHtmlName());
                } else {
                    this.converter.setHtmlTitle(param.getFileName());
                }
            } else {
                this.converter.setHtmlTitle(Config.htmlTitle);
            }
        } else {
            this.converter.setHtmlTitle("永中文档转换服务");
        }

        if (param.getBookMark() != null && param.getBookMark().length > 0) {
            this.converter.setBookmarks(param.getBookMark());
        }

        IPICConvertor image = null;
        IHtmlConvertor htmlconvertor = null;
        Integer re = ResultCode.E_CONVERSION_FAIL.getValue();
        Integer pagecount = null;

        DefaultResult var13;
        try {
            Integer page;
            int var36;
            String[] pdfMergeFile;
            int var9;
            Integer[] var10;
            Integer[] pages;
            int i;
            boolean result;
            label1010:
            switch(EnumConvertType.getEnumByType(param.getType()).ordinal()+1) {
                case 1:
                    re = this.converter.convertMStoHtmlOfSvg(param.getInput(), param.getOutput());
                    break;
                case 2:
                    re = this.converter.convertMStoHTML(param.getInput(), param.getOutput());
                    break;
                case 3:
                    re = this.converter.convertMStoTxt(param.getInput(), param.getOutput());
                    break;
                case 4:
                    re = this.converter.convertMStoPDF(param.getInput(), param.getOutput());
                    break;
                case 5:
                case 10:
                    if (param.getType() == EnumConvertType.MS_GIF.getType()) {
                        image = this.converter.convertMStoPic(param.getInput());
                    } else {
                        image = this.converter.convertPdftoPic(param.getInput());
                    }

                    if (image == null || image.resultCode() != 0) {
                        break;
                    }

                    pagecount = image.getPageCount();
                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    var10 = pages;
                    var9 = pages.length;
                    var36 = 0;

                    while(true) {
                        if (var36 >= var9) {
                            break label1010;
                        }

                        page = var10[var36];
                        re = image.convertToGIF(page, page, param.getZoom(), param.getOutput());
                        if (!ResultCode.E_SUCCES.getValue().equals(re)) {
                            break label1010;
                        }

                        ++var36;
                    }
                case 6:
                case 11:
                    if (param.getType() == EnumConvertType.MS_PNG.getType()) {
                        image = this.converter.convertMStoPic(param.getInput());
                    } else {
                        image = this.converter.convertPdftoPic(param.getInput());
                    }

                    if (image == null || image.resultCode() != 0) {
                        break;
                    }

                    pagecount = image.getPageCount();
                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    var10 = pages;
                    var9 = pages.length;
                    var36 = 0;

                    while(true) {
                        if (var36 >= var9) {
                            break label1010;
                        }

                        page = var10[var36];
                        re = image.convertToPNG(page, page, param.getZoom(), param.getOutput());
                        if (!ResultCode.E_SUCCES.getValue().equals(re)) {
                            break label1010;
                        }

                        ++var36;
                    }
                case 7:
                case 12:
                    if (param.getType() == EnumConvertType.MS_JPG.getType()) {
                        image = this.converter.convertMStoPic(param.getInput());
                    } else {
                        image = this.converter.convertPdftoPic(param.getInput());
                    }

                    if (image == null || image.resultCode() != 0) {
                        break;
                    }

                    pagecount = image.getPageCount();
                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    var10 = pages;
                    var9 = pages.length;
                    var36 = 0;

                    while(true) {
                        if (var36 >= var9) {
                            break label1010;
                        }

                        page = var10[var36];
                        re = image.convertToJPG(page, page, param.getZoom(), param.getOutput());
                        if (!ResultCode.E_SUCCES.getValue().equals(re)) {
                            break label1010;
                        }

                        ++var36;
                    }
                case 8:
                case 13:
                    if (param.getType() == EnumConvertType.MS_TIFF.getType()) {
                        image = this.converter.convertMStoPic(param.getInput());
                    } else {
                        image = this.converter.convertPdftoPic(param.getInput());
                    }

                    if (image == null || image.resultCode() != 0) {
                        break;
                    }

                    pagecount = image.getPageCount();
                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    var10 = pages;
                    var9 = pages.length;
                    var36 = 0;

                    while(true) {
                        if (var36 >= var9) {
                            break label1010;
                        }

                        page = var10[var36];
                        re = image.convertToTIFF(page, page, param.getZoom(), param.getOutput());
                        if (!ResultCode.E_SUCCES.getValue().equals(re)) {
                            break label1010;
                        }

                        ++var36;
                    }
                case 9:
                case 14:
                    if (param.getType() == EnumConvertType.MS_BMP.getType()) {
                        image = this.converter.convertMStoPic(param.getInput());
                    } else {
                        image = this.converter.convertPdftoPic(param.getInput());
                    }

                    if (image == null || image.resultCode() != 0) {
                        break;
                    }

                    pagecount = image.getPageCount();
                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    var10 = pages;
                    var9 = pages.length;
                    var36 = 0;

                    while(true) {
                        if (var36 >= var9) {
                            break label1010;
                        }

                        page = var10[var36];
                        re = image.convertToBMP(page, page, param.getZoom(), param.getOutput());
                        if (!ResultCode.E_SUCCES.getValue().equals(re)) {
                            break label1010;
                        }

                        ++var36;
                    }
                case 15:
                    re = this.converter.convertPdfToHtml(param.getInput(), param.getOutput());
                    break;
                case 16:
                    result = this.converter.convertHTMLToMS(param.getInput(), param.getOutput());
                    re = result ? ResultCode.E_SUCCES.getValue() : ResultCode.E_CONVERSION_FAIL.getValue();
                    break;
                case 17:
                case 19:
                    image = this.converter.convertMStoPic(param.getInput());
                    if (image == null || image.resultCode() != 0) {
                        break;
                    }

                    pagecount = image.getPageCount();
                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    var10 = pages;
                    var9 = pages.length;
                    var36 = 0;

                    while(true) {
                        if (var36 >= var9) {
                            break label1010;
                        }

                        page = var10[var36];
                        int page2 = page;
                        re = image.convertToSVG(page2, page2, 1.0F, param.getOutput());
                        if (!ResultCode.E_SUCCES.getValue().equals(re)) {
                            break label1010;
                        }

                        ++var36;
                    }
                case 18:
                    re = this.converter.convertTifToHtml(param.getInput(), param.getOutput());
                    break;
                case 20:
                    String addPath;
                    if (param.getHtmlName() != null && !"".equals(param.getHtmlName())) {
                        addPath = param.getHtmlName();
                    } else {
                        addPath = param.getFileName() == null ? "NewFile" : param.getFileName();
                    }

                    String filename = addPath;
                    addPath = addPath.indexOf(".") > 0 ? addPath.substring(0, addPath.lastIndexOf(".")) : addPath;
                    this.converter.unCompress(param.getInput(), param.getOutput() + File.separator + addPath, true);
                    FileService.copyFile(param.getInput(), param.getOutput(), filename);
                    re = ResultCode.E_SUCCES.getValue();
                    break;
                case 21:
                case 22:
                    result = FileService.copyFile(param.getInput(), param.getOutput(), param.getFileName().replaceAll(" ", ""));
                    re = result ? ResultCode.E_SUCCES.getValue() : ResultCode.E_CONVERSION_FAIL.getValue();
                    break;
                case 23:
                    pdfMergeFile = new String[param.getMergeInput().length + 1];
                    System.arraycopy(param.getMergeInput(), 0, pdfMergeFile, 1, param.getMergeInput().length);
                    pdfMergeFile[0] = param.getInput();
                    boolean result1 = this.converter.convertForMerge(pdfMergeFile, param.getOutput());
                    re = result1 ? ResultCode.E_SUCCES.getValue() : ResultCode.E_CONVERSION_FAIL.getValue();
                    break;
                case 24:
                    re = this.converter.convertPicToHtml(param.getInput(), param.getOutput());
                    break;
                case 25:
                    re = this.converter.convertPdfToTxt(param.getInput(), param.getOutput());
                    break;
                case 26:
                case 27:
                    htmlconvertor = this.converter.convertMStoHtml(param.getInput());
                    if (htmlconvertor == null || htmlconvertor.resultCode() != 0) {
                        break;
                    }

                    pagecount = htmlconvertor.getPageCount();
                    if (pagecount <= 0) {
                        break;
                    }

                    pages = param.getPage();
                    if (pages == null || pages.length < 1) {
                        pages = new Integer[pagecount];

                        for(i = 0; i < pagecount; ++i) {
                            pages[i] = i;
                        }
                    }

                    if (EnumConvertType.MS_HTML_PAGE.getType().equals(param.getType())) {
                        htmlconvertor.setNormal(true);
                    }

                    var10 = pages;
                    var9 = pages.length;

                    for(var36 = 0; var36 < var9; ++var36) {
                        page = var10[var36];
                        htmlconvertor.convertToHtml(param.getOutput() + File.separator + (page + 1) + ".html", page);
                    }

                    re = ResultCode.E_SUCCES.getValue();
                    break;
                case 28:
                case 29:
                    if (param.getType() == EnumConvertType.GET_PAGECONUT_MS.getType()) {
                        image = this.converter.convertMStoPic(param.getInput());
                    } else {
                        image = this.converter.convertPdftoPic(param.getInput());
                    }

                    if (image != null && image.resultCode() == 0) {
                        pagecount = image.getPageCount();
                        re = ResultCode.E_SUCCES.getValue();
                    }
                    break;
                case 30:
                    re = this.converter.convertMStoOFD(param.getInput(), param.getOutput());
                    break;
                case 31:
                    re = this.converter.convertMSToHtmlOfPic(param.getInput(), param.getOutput());
                    break;
                case 32:
                    pdfMergeFile = new String[param.getMergeInput().length + 1];
                    System.arraycopy(param.getMergeInput(), 0, pdfMergeFile, 1, param.getMergeInput().length);
                    pdfMergeFile[0] = param.getInput();
                    this.converter.mergePdfFiles(pdfMergeFile, param.getOutput(), true);
                    File file = new File(param.getOutput() == null ? "" : param.getOutput());
                    boolean result2;
                    if (file.exists()) {
                        result2 = true;
                    } else {
                        result2 = false;
                    }

                    re = result2 ? ResultCode.E_SUCCES.getValue() : ResultCode.E_CONVERSION_FAIL.getValue();
                    break;
                default:
                    re = ResultCode.E_INVALID_PARAM.getValue();
            }

            if (!ResultCode.E_SUCCES.getValue().equals(re) && !ResultCode.E_CONVERSION_TIMEOUT.getValue().equals(re)) {
                var13 = DefaultResult.failResult(ResultCode.getEnum(re).getInfo(), re);
                return var13;
            }

            var13 = DefaultResult.successResult(ResultCode.getEnum(re).getInfo(), pagecount);
        } catch (Exception var27) {
            LogUtil.error(var27);
            var13 = DefaultResult.failResult();
            return var13;
        } finally {
            this.converter.deleteTempFiles();
            if (image != null) {
                try {
                    image.close();
                } catch (Exception var26) {
                }
            }

            if (htmlconvertor != null) {
                try {
                    htmlconvertor.close();
                } catch (Exception var25) {
                }
            }

        }

        return var13;
    }
}
