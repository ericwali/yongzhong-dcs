//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.cons;

public enum EnumConvertType {
    MS_HTML_SVG(0, "ms2htmlsvg", "文档格式到高清html的转换", "html", false),
    MS_HTML(1, "ms2html", "文档格式到html的转换", "html", false),
    MS_TXT(2, "ms2txt", "文档格式到txt的转换", "txt", false),
    MS_PDF(3, "ms2pdf", "文档格式到pdf的转换", "pdf", false),
    MS_GIF(4, "ms2gif", "文档格式到gif的转换", "gif", true),
    MS_PNG(5, "ms2png", "文档格式到png的转换", "png", true),
    MS_JPG(6, "ms2jpg", "文档格式到jpg的转换", "jpg", true),
    MS_TIFF(7, "ms2tiff", "文档格式到tiff的转换", "tiff", true),
    MS_BMP(8, "ms2bmp", "文档格式到bmp的转换", "bmp", true),
    PDF_GIF(9, "pdf2gif", "pdf文档格式到gif的转换", "gif", true),
    PDF_PNG(10, "pdf2png", "pdf文档格式到png的转换", "png", true),
    PDF_JPG(11, "pdf2jpg", "pdf文档格式到jpg的转换", "jpg", true),
    PDF_TIFF(12, "pdf2tiff", "pdf文档格式到tiff的转换", "tiff", true),
    PDF_BMP(13, "pdf2bmp", "pdf文档格式到bmp的转换", "bmp", true),
    PDF_HTML(14, "pdf2html", "pdf文档格式到html的转换", "html", false),
    HTML_MS(15, "html2ms", "html文档格式到微软文档格式的转换", "ms", true),
    MS_SVG_TEMP(16, "ms2svgtemp", "文档转换多个SVG返回分页加载页面", "svg", true),
    TIF_HTML(17, "tif2html", "tif文件转成html", "html", false),
    MS_SVG(18, "ms2svg", "文档转换多个SVG", "svg", true),
    ZIP_HTML_TEMP(19, "zip2html", "压缩文件到html的转换", "zip", true),
    PDF_HTML_TEMP(20, "pdf2htmltemp", "PDF文件到html的转换", "html", true),
    OFD_HTML_TEMP(21, "ofd2htmltemp", "ofd文件到html的转换", "html", true),
    MS_MERGE(22, "ms2merge", "两个doc文档合并", "doc", false),
    PIC_HTML(23, "pic2html", "图片到html的转换", "html", false),
    PDF_TXT(24, "pdf2txt", "pdf文档格式到txt的转换", "txt", false),
    MS_SVG_PAGE(25, "ms2svgpage", "文档按页转换（高清版）", "html", true),
    MS_HTML_PAGE(26, "ms2htmlpage", "文档按页转换（标准版）", "html", true),
    GET_PAGECONUT_MS(27, "getPageMS", "只获取文档页码", "html", false),
    GET_PAGECONUT_PDF(28, "getPagePDF", "只获取pdf页码", "html", false),
    MS_OFD(29, "ms2ofd", "文档转ofd格式", "ofd", false),
    MS_HTMLOFPIC(30, "ms2htmlofpic", "文档转html(图片)", "html", false),
    PDF_MERGE(31, "pdf2merge", "多pdf文档合并", "pdf", false);

    private Integer value;
    private String type;
    private String info;
    private String ext;
    private boolean isImageType;

    public Integer getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    public String getInfo() {
        return this.info;
    }

    public String getExt() {
        return this.ext;
    }

    public boolean isImageType() {
        return this.isImageType;
    }

    private EnumConvertType(Integer value, String type, String info, String ext, boolean isImageType) {
        this.value = value;
        this.type = type;
        this.info = info;
        this.ext = ext;
        this.isImageType = isImageType;
    }

    public static EnumConvertType getEnum(int value) {
        EnumConvertType[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            EnumConvertType code = var4[var2];
            if (value == code.getValue()) {
                return code;
            }
        }

        return null;
    }

    public static boolean isMerge(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        if (enumConvertType != null) {
            switch(EnumConvertType.getEnum(enumConvertType.ordinal()).getValue()) {
                case 23:
                case 32:
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isTemplet(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        if (enumConvertType != null) {
            switch(EnumConvertType.getEnum(enumConvertType.ordinal()).getValue()) {
                case 17:
                case 20:
                case 21:
                case 22:
                    return true;
                case 18:
                case 19:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isResultPage(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        if (enumConvertType != null) {
            switch(EnumConvertType.getEnum(enumConvertType.ordinal()).getValue()) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 17:
                case 19:
                case 26:
                case 27:
                case 28:
                case 29:
                    return true;
                case 15:
                case 16:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isIncludePdf(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        if (enumConvertType != null) {
            switch(EnumConvertType.getEnum(enumConvertType.ordinal()).getValue()) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 21:
                case 25:
                case 29:
                case 30:
                case 32:
                    return true;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 22:
                case 23:
                case 24:
                case 26:
                case 27:
                case 28:
                case 31:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isPdfOperation(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        if (enumConvertType != null) {
            switch(EnumConvertType.getEnum(enumConvertType.ordinal()).getValue()) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 21:
                case 25:
                case 29:
                case 32:
                    return true;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 22:
                case 23:
                case 24:
                case 26:
                case 27:
                case 28:
                case 30:
                case 31:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isImageTarget(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        return enumConvertType != null ? enumConvertType.isImageType : false;
    }

    public static String getTargetType(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        return enumConvertType != null ? enumConvertType.ext : "";
    }

    public static Integer getTargetValue(String type) {
        EnumConvertType enumConvertType = getEnumByType(type);
        return enumConvertType != null ? enumConvertType.value : null;
    }

    public static EnumConvertType getEnumByType(String type) {
        EnumConvertType[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            EnumConvertType code = var4[var2];
            if (type == code.getType()) {
                return code;
            }
        }

        return null;
    }

    public static IResult<EnumConvertType> checkParame(String convertTypeParame) {
        if (convertTypeParame == null) {
            return DefaultResult.failResult();
        } else {
            try {
                Integer convertType = Integer.parseInt(convertTypeParame);
                EnumConvertType result = getEnum(convertType);
                return result == null ? DefaultResult.failResult() : DefaultResult.successResult(result);
            } catch (Exception var3) {
                return DefaultResult.failResult();
            }
        }
    }
}
