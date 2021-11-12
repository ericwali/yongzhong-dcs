//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Config {
    private static Properties properties;
    public static File rootDir;
    public static File inputDir;
    public static File outputDir;
    public static File tempDir;
    public static File wmImagePath;
    public static int convertPoolSize;
    public static int convertTimeout;
    public static String staticPath;
    public static String pdfStaticPath;
    public static String folderFormat;
    public static long uploadSize;
    public static long clearDay;
    public static String htmlTitle;
    public static String filePermission;
    public static String showFooter;
    public static String supportCross;
    public static String setOutFilename;
    public static String AcceptTracks;
    public static String domain;
    public static int connectTimeout;
    public static int socketTimeout;
    public static int maxTotal;
    public static int defaultMaxPerRoute;

    public Config() {
    }

    public static void init(String root) throws Throwable {
        rootDir = new File(root);
        properties = new Properties();

        try {
            Throwable var1 = null;
            Object var2 = null;

            try {
                FileInputStream fis = new FileInputStream(new File(rootDir, "WEB-INF/config.properties"));

                try {
                    InputStreamReader reader = new InputStreamReader(fis, "utf-8");

                    try {
                        properties.load(reader);

                        try {
                            inputDir = new File(getString("dir.input"));
                        } catch (Exception var97) {
                            inputDir = new File(rootDir, "input");
                        }

                        try {
                            outputDir = new File(getString("dir.output"));
                        } catch (Exception var96) {
                            outputDir = new File(rootDir, "output");
                        }

                        staticPath = getString("staticPath");
                        pdfStaticPath = getString("pdfStaticPath");
                        if (pdfStaticPath == null) {
                            pdfStaticPath = staticPath;
                        }

                        try {
                            folderFormat = getString("folderFormat");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(folderFormat);
                            System.out.println(simpleDateFormat.format(new Date()));
                        } catch (Exception var95) {
                            folderFormat = null;
                        }

                        try {
                            htmlTitle = getString("htmlTitle");
                        } catch (Exception var94) {
                            htmlTitle = null;
                        }

                        try {
                            filePermission = getString("FilePermission");
                        } catch (Exception var93) {
                            filePermission = "false";
                        }

                        try {
                            AcceptTracks = getString("AcceptTracks");
                        } catch (Exception var92) {
                            AcceptTracks = "false";
                        }

                        try {
                            showFooter = getString("showFooter");
                        } catch (Exception var91) {
                            showFooter = "false";
                        }

                        try {
                            supportCross = getString("supportCross");
                        } catch (Exception var90) {
                            supportCross = "true";
                        }

                        try {
                            setOutFilename = getString("setOutFilename");
                        } catch (Exception var89) {
                            setOutFilename = "false";
                        }

                        try {
                            domain = getString("domain");
                        } catch (Exception var88) {
                            domain = null;
                        }

                        try {
                            clearDay = (long)getInt("clearDay");
                        } catch (Exception var87) {
                            clearDay = 0L;
                        }

                        try {
                            convertPoolSize = getInt("convert.pool.size");
                            convertPoolSize = convertPoolSize < 1 ? 1 : convertPoolSize;
                        } catch (Exception var86) {
                            convertPoolSize = 1;
                        }

                        try {
                            convertTimeout = getInt("convert.timeout");
                        } catch (Exception var85) {
                            convertTimeout = 60;
                        }

                        try {
                            connectTimeout = getInt("connectTimeout");
                        } catch (Exception var84) {
                            connectTimeout = 15000;
                        }

                        try {
                            maxTotal = getInt("maxTotal");
                        } catch (Exception var83) {
                            maxTotal = convertPoolSize * 15;
                        }

                        try {
                            defaultMaxPerRoute = getInt("defaultMaxPerRoute");
                        } catch (Exception var82) {
                            defaultMaxPerRoute = convertPoolSize;
                        }

                        try {
                            socketTimeout = getInt("socketTimeout");
                        } catch (Exception var81) {
                            socketTimeout = 15000;
                        }

                        try {
                            uploadSize = (long)getInt("uploadSize") * 1024L * 1024L;
                        } catch (Exception var80) {
                            uploadSize = 5000000L;
                        }

                        try {
                            tempDir = new File(getString("TempPath"));
                            tempDir.mkdirs();
                        } catch (Exception var79) {
                            tempDir = null;
                        }

                        try {
                            wmImagePath = new File(getString("WmImagePath"));
                            wmImagePath.mkdirs();
                        } catch (Exception var78) {
                            wmImagePath = null;
                        }

                        if (convertPoolSize <= 0) {
                            convertPoolSize = 5;
                        }

                        if (!inputDir.exists()) {
                            inputDir.mkdirs();
                        }

                        if (!outputDir.exists()) {
                            outputDir.mkdirs();
                        }
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }

                    }
                } catch (Throwable var99) {
                    if (var1 == null) {
                        var1 = var99;
                    } else if (var1 != var99) {
                        var1.addSuppressed(var99);
                    }

                    if (fis != null) {
                        fis.close();
                    }

                    throw var1;
                }

                if (fis != null) {
                    fis.close();
                }
            } catch (Throwable var100) {
                if (var1 == null) {
                    var1 = var100;
                } else if (var1 != var100) {
                    var1.addSuppressed(var100);
                }

                throw var1;
            }
        } catch (Exception var101) {
            var101.printStackTrace();
            System.out.println("config.properties配置出错，未运行成功，请检查后，重新启动……");
        }

    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.valueOf(properties.getProperty(key).trim());
    }
}
