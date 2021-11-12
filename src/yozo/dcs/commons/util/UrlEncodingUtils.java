//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.commons.util;

import org.apache.catalina.util.URLEncoder;

import java.lang.Character.UnicodeBlock;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlEncodingUtils {
    public UrlEncodingUtils() {
    }

    public static void main(String[] args) throws URISyntaxException {
        String url2 = "http://58.215.166.234/example/doc/doctest1 - 副本.docx";
        String url3 = "http://saasfile.swartz.cn/SaaS2016110821472634286?Expires=1478765624&OSSAccessKeyId=vmAVXWDAixQIiape&Signature=ak0hLXUM7xvHp28OfJeKjJ%2FAL48%3D";
        String url4 = "http://nearbucket.oss-cn-shanghai.aliyuncs.com/%E6%8A%A5%E5%91%8A.pdf?Expires=1480230715&OSSAccessKeyId=TMP.AQFjclHEqVweMQb2uRKqpYbSoVfGL71s5eVoXxVtnaSxeIT16B4dwSDntv-fADAtAhRO7z7S5oENSMxk1wPwonpOAzYYEwIVANM69bp5TGjgZSCMrBd7otpjYuJB&Signature=OmjsFez6b71ZzPy2huubxyf6ZZM%3D";
        String url5 = "http://61.139.94.30:2016/UploadFile/GongWenFile/20161124/24140602596_%E4%BC%9A%E8%AE%AE%E7%BA%AA%E8%A6%81[2016]95%E5%8F%B7.doc";
        String url6 = "http://175.173.244.157:8066/WebService.asmx/../../files/ERD/CustomerFolder/c8982f61-5c4d-4d23-a7ba-6a0fbdae579b.pdf";
        System.out.println(symbolHandle(url6));
    }

    public static String symbolHandle(String url) {
        return encodingUrl(folderUrl(url));
    }

    private static String folderUrl(String url) {
        Pattern p = Pattern.compile("^(http|https)://[^/]+/", 2);
        Matcher m = p.matcher(url);
        if (!m.find()) {
            return url;
        } else {
            String hreadStr = m.group();
            url = url.replace(hreadStr, "");

            for(url = url.replaceAll("[^/]+/\\.\\./", ""); url.startsWith("../"); url = url.substring(3)) {
            }

            return hreadStr + url;
        }
    }

    private static String encodingUrl(String url) {
        Pattern p = Pattern.compile("^(http|https)://[^/]+/", 2);
        Matcher m = p.matcher(url);
        if (!m.find()) {
            return url;
        } else {
            StringBuffer re = new StringBuffer();
            int isSplit = url.indexOf("?");
            String urlPart1 = null;
            String urlPart2 = null;
            if (isSplit > -1) {
                urlPart1 = url.substring(0, isSplit);
                urlPart2 = url.substring(isSplit);
            } else {
                urlPart1 = url;
            }

            String[] urlSub = urlPart1.split("/");
            int urlSublen = urlSub.length;

            for(int i = 0; i < urlSublen; ++i) {
                String temp = urlSub[i];
                if (i < 3) {
                    re.append(temp);
                } else {
                    String[] tempSub = temp.split("%");
                    int tempSubLen = tempSub.length;

                    for(int j = 0; j < tempSubLen; ++j) {
                        String t = tempSub[j];
                        re.append((new URLEncoder()).encode(t));
                        if (j + 1 < tempSubLen) {
                            re.append("%");
                        }
                    }
                }

                if (i + 1 < urlSublen) {
                    re.append("/");
                }
            }

            String result = re.toString().replaceAll("%2E", ".");
            if (urlPart2 != null && !"".equals(urlPart2)) {
                result = result + urlPart2;
            }

            return result;
        }
    }

    private static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();

        for(int i = 0; i < ch.length; ++i) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
            return pattern.matcher(str.trim()).find();
        }
    }

    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        } else {
            String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
            Pattern pattern = Pattern.compile(reg);
            return pattern.matcher(str.trim()).find();
        }
    }
}
