//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.log;

import org.apache.log4j.Logger;

public class LogUtil {
    private static final Logger logger = Logger.getLogger("");
    public static final String lineSep = System.getProperty("line.separator");

    public LogUtil() {
    }

    public static void main(String[] args) {
        String sOut = "";
        StackTraceElement[] trace = (new Exception()).getStackTrace();
        StackTraceElement[] var6 = trace;
        int var5 = trace.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            StackTraceElement s = var6[var4];
            sOut = sOut + "\tat " + s + "\r\n";
        }

        (new Exception()).printStackTrace();
        System.out.println(sOut);
    }

    public static void error(Throwable t) {
        logger.error("", t);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message, Exception e) {
        logger.warn(message, e);
    }

    public static void warn(String value) {
        logger.warn(value);
    }

    public static void error(Exception e) {
        String sOut = e.toString() + "\r\n";
        StackTraceElement[] trace = (new Exception()).getStackTrace();
        StackTraceElement[] var6 = trace;
        int var5 = trace.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            StackTraceElement s = var6[var4];
            sOut = sOut + "\tat " + s + "\r\n";
        }

        logger.error(sOut);
    }
}
