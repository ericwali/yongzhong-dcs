//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.bo;

import yozo.dcs.web.cons.EnumColor;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.listener.Config;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;

public class ConvertParamBuilder {
    private ConvertParam convertParam = new ConvertParam();

    public ConvertParamBuilder() {
    }

    public ConvertParam buildParam(HttpServletRequest request) {
        this.convertParam.setPage(this.getPages(request.getParameter("pages")));
        this.convertParam.setCallbackName(request.getParameter("callbackName"));
        this.convertParam.setEncoding(request.getParameter("encoding"));
        this.convertParam.setAppendPath(request.getParameter("appendPath"));
        this.convertParam.setDestinationName(request.getParameter("destinationName"));

        try {
            this.convertParam.setHtmlName(URLDecoder.decode(request.getParameter("htmlName"), "utf-8"));
        } catch (Exception var32) {
        }

        try {
            this.convertParam.setHtmlTitle(URLDecoder.decode(request.getParameter("htmlTitle"), "utf-8"));
        } catch (Exception var31) {
        }

        String inputDir = request.getParameter("inputDir");
        String outputDir = request.getParameter("outputDir");
        inputDir = this.updateFileSeparator(inputDir);
        outputDir = this.updateFileSeparator(outputDir);
        String finput = request.getAttribute("finput") == null ? null : "" + request.getAttribute("finput");
        String realInput = "";
        if (inputDir != null && !"".equals(inputDir)) {
            realInput = Config.inputDir + File.separator + inputDir;
        } else if (outputDir != null && !"".equals(outputDir)) {
            realInput = Config.outputDir + File.separator + outputDir;
        } else if (finput != null && !"".equals(finput)) {
            realInput = finput;
        }

        this.convertParam.setInput(realInput);
        String[] mergeInput = this.getMerge(request.getParameter("mergeInput"));
        if (mergeInput != null && !"".equals(mergeInput)) {
            for(int i = 0; i < mergeInput.length; ++i) {
                mergeInput[i] = Config.inputDir + File.separator + this.updateFileSeparator(mergeInput[i]);
            }

            this.convertParam.setMergeInput(mergeInput);
        }

        String convertTypeParame = request.getParameter("convertType");
        String auto = request.getParameter("auto");
        EnumConvertType enumType = null;

        String isHd;
        String font;
        try {
            enumType = EnumConvertType.getEnum(Integer.parseInt(convertTypeParame));
        } catch (Exception var34) {
            if (auto != null && auto.equals("1")) {
                isHd = request.getParameter("isHd");
                font = realInput.toLowerCase();
                if (!font.endsWith(".doc") && !font.endsWith(".docx") && !font.endsWith(".xls") && !font.endsWith(".xlsx") && !font.endsWith(".ppt") && !font.endsWith(".pptx") && !font.endsWith(".txt") && !font.endsWith(".xml") && !font.endsWith(".properties")) {
                    if (font.endsWith(".pdf")) {
                        if ("1".equals(isHd)) {
                            enumType = EnumConvertType.PDF_HTML_TEMP;
                        } else {
                            enumType = EnumConvertType.PDF_HTML;
                        }
                    } else if (!font.endsWith(".tif") && !font.endsWith(".tiff")) {
                        if (!font.endsWith(".gif") && !font.endsWith(".ico") && !font.endsWith(".jpg") && !font.endsWith(".jpeg") && !font.endsWith(".jpe") && !font.endsWith(".png") && !font.endsWith(".bmp")) {
                            if (!font.endsWith(".zip") && !font.endsWith(".rar")) {
                                enumType = EnumConvertType.MS_HTML;
                            } else {
                                enumType = EnumConvertType.ZIP_HTML_TEMP;
                            }
                        } else {
                            enumType = EnumConvertType.PIC_HTML;
                        }
                    } else {
                        enumType = EnumConvertType.TIF_HTML;
                    }
                } else if (isHd != null && !"".equals(isHd) && isHd.equals("1")) {
                    enumType = EnumConvertType.MS_HTML_SVG;
                } else {
                    enumType = EnumConvertType.MS_HTML;
                }
            } else {
                enumType = null;
            }
        }

        this.convertParam.setOutputByType(enumType);
        String fname = request.getAttribute("fname") == null ? null : "" + request.getAttribute("fname");
        isHd = request.getParameter("fileName");
        if (isHd != null && !"".equals(isHd)) {
            this.convertParam.setFileName(request.getParameter("fileName"));
        } else if (fname != null && !"".equals(fname)) {
            if (enumType != null) {
                fname = this.updataPdfFileName(enumType.getType(), realInput);
            }

            this.convertParam.setFileName(fname);
        } else {
            try {
                int fileSpeInx = realInput.lastIndexOf(File.separator);
                isHd = fileSpeInx > 0 ? realInput.substring(fileSpeInx + 1) : "NewFile";
                this.convertParam.setFileName(isHd);
            } catch (Exception var30) {
                this.convertParam.setFileName("NewFile");
            }
        }

        if (enumType != null) {
            this.updataPdfFilePath(enumType.getType(), realInput);
        }

        try {
            this.convertParam.setIsSourceDir(Integer.parseInt(request.getParameter("isSourceDir")));
        } catch (Exception var29) {
            this.convertParam.setIsSourceDir(0);
        }

        try {
            this.convertParam.setIsCopy(Integer.parseInt(request.getParameter("isCopy")));
        } catch (Exception var28) {
            this.convertParam.setIsCopy(0);
        }

        try {
            this.convertParam.setIsShowTitle(Integer.parseInt(request.getParameter("isShowTitle")));
        } catch (Exception var27) {
            this.convertParam.setIsShowTitle(1);
        }

        try {
            this.convertParam.setIsShowList(Integer.parseInt(request.getParameter("isShowList")));
        } catch (Exception var26) {
            this.convertParam.setIsShowList(1);
        }

        try {
            this.convertParam.setIsShowAll(Integer.parseInt(request.getParameter("isShowAll")));
        } catch (Exception var25) {
            this.convertParam.setIsShowAll(0);
        }

        try {
            if (Config.showFooter != null && !"".equals(Config.showFooter) && Config.showFooter.equals("false")) {
                this.convertParam.setShowFooter(0);
            } else {
                this.convertParam.setShowFooter(1);
            }

            this.convertParam.setShowFooter(Integer.parseInt(request.getParameter("showFooter")));
        } catch (Exception var33) {
        }

        try {
            this.convertParam.setPassword(request.getParameter("password"));
        } catch (Exception var24) {
            this.convertParam.setPassword("");
        }

        try {
            this.convertParam.setIsDelSrc(Integer.parseInt(request.getParameter("isDelSrc")));
        } catch (Exception var23) {
            this.convertParam.setIsDelSrc(0);
        }

        try {
            this.convertParam.setIsDelTarget(Integer.parseInt(request.getParameter("isDelTarget")));
        } catch (Exception var22) {
            this.convertParam.setIsDelTarget(0);
        }

        try {
            this.convertParam.setIsZip(Integer.parseInt(request.getParameter("isZip")));
        } catch (Exception var21) {
            this.convertParam.setIsZip(0);
        }

        Integer color;
        try {
            color = Integer.parseInt(request.getParameter("startPage"));
            this.convertParam.setStartPage(color >= 0 ? color : -1);
        } catch (Exception var20) {
            this.convertParam.setStartPage(-1);
        }

        try {
            color = Integer.parseInt(request.getParameter("endPage"));
            this.convertParam.setEndPage(color >= 1 ? color : -1);
        } catch (Exception var19) {
            this.convertParam.setEndPage(-1);
        }

        try {
            color = Integer.parseInt(request.getParameter("wmSize"));
            this.convertParam.setWmSize(color > 0 ? color : 100);
        } catch (Exception var18) {
            this.convertParam.setWmSize(100);
        }

        try {
            color = Integer.parseInt(request.getParameter("wmColor"));
            this.convertParam.setWmColor(EnumColor.getColor(color));
        } catch (Exception var17) {
            this.convertParam.setWmColor(EnumColor.GRAY.getColor());
        }

        font = request.getParameter("wmFont");
        String content = request.getParameter("wmContent");
        if (font != null && !"".equals(font)) {
            this.convertParam.setWmFont(font);
        } else {
            this.convertParam.setWmFont("宋体");
        }

        this.convertParam.setWmContent(content);
        String wmPicPath = request.getParameter("wmPicPath");
        if (wmPicPath != null && !"".equals(wmPicPath)) {
            wmPicPath = Config.wmImagePath + File.separator + wmPicPath;
            this.convertParam.setWmPicPath(wmPicPath);
        }

        try {
            Float zoom = Float.parseFloat(request.getParameter("zoom"));
            if (zoom <= 0.0F) {
                this.convertParam.setZoom(1.0F);
            } else {
                this.convertParam.setZoom(zoom);
            }
        } catch (Exception var16) {
            this.convertParam.setZoom(1.0F);
        }

        this.convertParam.setBookMark(this.getBookMark(request));
        return this.convertParam;
    }

    private String[] getBookMark(HttpServletRequest request) {
        List<String> result = new LinkedList();
        Enumeration parameterNames = request.getParameterNames();

        while(parameterNames.hasMoreElements()) {
            String tempName = (String)parameterNames.nextElement();
            if (tempName.startsWith("bookMarkName")) {
                String varP = tempName.replace("bookMarkName", "");
                String bookMarkName = request.getParameter(tempName);
                String bookMarkValue = request.getParameter("bookMarkValue" + varP);
                result.add(bookMarkName);
                result.add(bookMarkValue);
            }
        }

        String[] bookMark = new String[result.size()];
        result.toArray(bookMark);
        return bookMark;
    }

    private String updateFileSeparator(String filepath) {
        if (filepath != null) {
            filepath = filepath.replaceAll("\\.\\.", "");
            filepath = filepath.replaceAll("(\\\\|/)", Matcher.quoteReplacement(File.separator));
        }

        return filepath;
    }

    private String updataPdfFileName(String convertType, String filepath) {
        String filename = filepath.substring(filepath.lastIndexOf(File.separator) + 1);
        if (EnumConvertType.isPdfOperation(convertType)) {
            String newName = "";
            if (filename.toLowerCase().endsWith(".pdf")) {
                return filename;
            } else {
                if (filename.lastIndexOf(".") == -1) {
                    newName = filename + ".pdf";
                } else {
                    newName = filename.substring(0, filename.lastIndexOf(".")) + ".pdf";
                }

                return newName;
            }
        } else {
            return filename;
        }
    }

    private void updataPdfFilePath(String convertType, String filepath) {
        if (EnumConvertType.isPdfOperation(convertType)) {
            String path = filepath.substring(0, filepath.lastIndexOf(File.separator));
            String filename = filepath.substring(filepath.lastIndexOf(File.separator) + 1);
            String newName = this.updataPdfFileName(convertType, filepath);
            if (!filename.equals(newName)) {
                String newfilepath = path + File.separator + newName;
                File oldfile = new File(filepath);
                File newfile = new File(newfilepath);
                if (newfile.exists()) {
                    newfile.delete();
                }

                oldfile.renameTo(newfile);
                this.convertParam.setInput(newfilepath);
            }
        }

    }

    private String[] getMerge(String merges) {
        if (merges != null && !"".equals(merges)) {
            String[] vars = merges.split(",");
            String[] finalmerge = new String[vars.length];

            for(int i = 0; i < vars.length; ++i) {
                finalmerge[i] = vars[i];
            }

            return finalmerge;
        } else {
            return null;
        }
    }

    private Integer[] getPages(String pages) {
        if (pages != null && !"".equals(pages)) {
            Set<Integer> set = new HashSet();
            String[] vars = pages.split(",");
            String[] var7 = vars;
            int var6 = vars.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                String var = var7[var5];
                if (var.contains("-")) {
                    String[] start2end = var.split("-");
                    if (start2end.length == 2) {
                        try {
                            Integer start = Integer.parseInt(start2end[0]);
                            Integer end = Integer.parseInt(start2end[1]);
                            if (start <= end) {
                                for(int i = start; i <= end; ++i) {
                                    set.add(i - 1);
                                }
                            }
                        } catch (Exception var13) {
                        }
                    }
                } else {
                    try {
                        set.add(Integer.parseInt(var) - 1);
                    } catch (Exception var12) {
                    }
                }
            }

            return (Integer[])set.toArray(new Integer[set.size()]);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        String filepath2 = "/home\\admin/a.doc";
        String filepath = "E:\\dcs.web/a/a.doc";
        filepath2 = filepath2.replaceAll("(\\\\|/)", Matcher.quoteReplacement(File.separator));
        System.out.println(filepath2);
    }
}
