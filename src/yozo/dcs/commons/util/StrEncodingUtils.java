//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.commons.util;

import java.io.UnsupportedEncodingException;

public class StrEncodingUtils {
    public StrEncodingUtils() {
    }

    public static String getEncoding(String str) {
        String encode = "ISO-8859-1";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var6) {
        }

        encode = "GBK";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var5) {
        }

        encode = "UTF-8";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var4) {
        }

        encode = "GB2312";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var3) {
        }

        return null;
    }

    public static String TranEncode2CN(String str) {
        String strEncode = getEncoding(str);
        String value = str;
        if (strEncode != null && "ISO-8859-1".equals(strEncode)) {
            try {
                value = new String(str.getBytes(strEncode), "UTF-8");
            } catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();
            }
        }

        return value;
    }

    public static void main(String[] args) {
        System.out.println(TranEncode2CN("い地チ瓣"));
    }
}
