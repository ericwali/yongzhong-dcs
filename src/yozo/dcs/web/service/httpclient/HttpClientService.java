//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.httpclient;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import yozo.dcs.commons.util.StrEncodingUtils;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.listener.Config;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientService {
    public static final int cache = 10240;
    public static final String SUFFIXES = "docx|doc|pptx|ppt|xlsx|xls|rtf|eio|uof|uos|xml|pdf|ofd|jpeg|gif|jpg|png|txt|zip|rar|tif|properties|html|DOCX|DOC|PPTX|PPT|XLSX|XLS|RTF|EIO|UOF|UOS|XML|PDF|OFD|JPEG|GIF|JPG|PNG|TXT|ZIP|RAR|TIF|PROPERTIES|HTML";
    private static PoolingHttpClientConnectionManager connManager;
    private static RequestConfig requestConfig;

    public HttpClientService() {
    }

    public static void init() throws GeneralSecurityException {
        SSLContext sslcontext = SSLContext.getInstance("SSL");
        sslcontext.init((KeyManager[])null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, (SecureRandom)null);
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connManager.setMaxTotal(Config.maxTotal);
        connManager.setDefaultMaxPerRoute(Config.defaultMaxPerRoute);
        requestConfig = RequestConfig.custom().setSocketTimeout(Config.socketTimeout).setConnectTimeout(Config.connectTimeout).build();
    }

    public static IResult<String> download(String param0, String param1, String param2) {
        // $FF: Couldn't be decompiled
        return null;
    }

    public static IResult<Object> getfileprop(String param0) {
        // $FF: Couldn't be decompiled
        return null;
    }

    public static String getFileName(HttpResponse response, String url) {
        String filename = "";
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String type;
        String temp;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length >= 1) {
                type = values[0].getValue();
                NameValuePair param = values[0].getParameterByName("filename");
                if (type != null && !"".equals(type)) {
                    filename = StrEncodingUtils.TranEncode2CN(type);
                } else if (param != null) {
                    try {
                        temp = URLDecoder.decode(param.getValue(), "utf-8");
                        return StrEncodingUtils.TranEncode2CN(temp);
                    } catch (UnsupportedEncodingException var9) {
                        var9.printStackTrace();
                    }
                }
            }
        }

        Pattern FilePattern;
        if ("".equals(filename)) {
            FilePattern = Pattern.compile("[/]{1}[^/]+[\\.](?i)(docx|doc|pptx|ppt|xlsx|xls|rtf|eio|uof|uos|xml|pdf|ofd|jpeg|gif|jpg|png|txt|zip|rar|tif|properties|html|DOCX|DOC|PPTX|PPT|XLSX|XLS|RTF|EIO|UOF|UOS|XML|PDF|OFD|JPEG|GIF|JPG|PNG|TXT|ZIP|RAR|TIF|PROPERTIES|HTML)");

            try {
                new URLDecoder();
                Matcher mc = FilePattern.matcher(URLDecoder.decode(url, "UTF-8"));
                if (mc.find()) {
                    filename = URLDecoder.decode(mc.group().substring(1), "utf-8");
                }
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
            }
        }

        if ("".equals(filename)) {
            String name = "" + response.getFirstHeader("ETag");
            type = response.getFirstHeader("Content-Type").toString();
            if (!"".equals(name) && !"null".equals(name) && name != null && type != null && !"null".equals(type)) {
                String sname = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\""));
                temp = type.split("/")[1];
                filename = sname + "." + temp;
            }
        }

        if ("".equals(filename)) {
            filename = String.valueOf(System.currentTimeMillis());
        }

        FilePattern = Pattern.compile("[\\\\/:*?\"<>|]");
        filename = FilePattern.matcher(filename).replaceAll("");
        if (filename.length() > 255) {
            filename.substring(0, 255);
        }

        return filename;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException {
        String newUrl = null;
        String url = "http://beta.meiicloud.net:9480/sharefile?BaseUrl=beta.meiicloud.net&Token={4BAF3FA5-D3DC-4549-9D478A4ED06572BD}&Path=/__MySpace__/新建空白文档.doc";
        String url1 = "http://beta.meiicloud.net:9480/sharefile?BaseUrl=beta.meiicloud.net&Token=%7d4BAF3FA5-D3DC-4549-9D478A4ED06572BD%7d&Path=%2f__MySpace__%2f%e6%96%b0%e5%bb%ba%e7%a9%ba%e7%99%bd%e6%96%87%e6%a1%a3.doc";
        String url2 = "http://58.215.166.234/example/doc/doctest1 - 副本.docx";
        String url3 = "http://saasfile.swartz.cn/SaaS2016110821472634286?Expires=1478765624&OSSAccessKeyId=vmAVXWDAixQIiape&Signature=ak0hLXUM7xvHp28OfJeKjJ%2FAL48%3D";
        String url4 = "http://nearbucket.oss-cn-shanghai.aliyuncs.com/%E6%8A%A5%E5%91%8A.pdf?Expires=1480230715&OSSAccessKeyId=TMP.AQFjclHEqVweMQb2uRKqpYbSoVfGL71s5eVoXxVtnaSxeIT16B4dwSDntv-fADAtAhRO7z7S5oENSMxk1wPwonpOAzYYEwIVANM69bp5TGjgZSCMrBd7otpjYuJB&Signature=OmjsFez6b71ZzPy2huubxyf6ZZM%3D";
        String url5 = "http://61.139.94.30:2016/UploadFile/GongWenFile/20161124/24140602596_%E4%BC%9A%E8%AE%AE%E7%BA%AA%E8%A6%81[2016]95%E5%8F%B7.doc";
        String url6 = "http://slwly.xzh-soft.com:8680/attachment/11.doc";
        String url7 = "http://www.yozodcs.com/example/doc/doctest.docx";
        String str = "http://yscorppan.oss-cn-hangzhou.aliyuncs.com/cloudstorage/10000/2017/02/18/%E5%89%91%E6%A1%A5%E9%9B%85%E6%80%9D9%E9%AB%98%E6%B8%85%E5%AE%8C%E6%95%B4%E7%89%88.pDf?OSSAccessKeyId=STS.FaaGnujKhA2Zbj7dC9uuUqgR7&Expires=1488254593&Signature=aEpSwamRRjhW8hgQfToiunqVadQ%3D&security-token=CAIShAJ1q6Ft5B2yfSjIp6LUDNTBh5RJ9vCxYEyGgENsefp5nqL51Tz2IHBLe3hoB%2BAat/wwlW5W7v4Ylol9FM8eS0WVM8J%2BtsVdoFj4OoeZ4ZHrtrBbhsD4EjWdVEZyUkQto6arIunGc9KBNnrm9EYqs5aYGBymW1u6S%2B7r7bdsctUQWCShcDNCH604DwB%2BqcgcRxCzXLTXRXyMuGfLC1dysQdRkH527b/FoveR8R3Dllb3uIR3zsbTWsH9PpMwZs4mAojqgbUnKvX7vXQOu0QQxsBfl7dZ/DrLhNaZDmRK7g%2BOW%2BiuqY0wc14hOPhmQvYb/aaizaUioIHUjJ/10AaumCGyuNw4LhqAAZQR93yyMLN0ZYb8j2zex/oB5NrZ3TUEPaohZfpi6to8lEtTjWSVpf4BKio/QgjouhBverVjGpN3I5sw2nWu31HCX7hClqgJSaelYr2F21PYmp9avMclMfaJpURtPupkhQO9jBHzUV4TZ8jlkDxOUNeS976kd1c44qZYxcJR235T";
        Pattern pat = Pattern.compile("[/]{1}[^/]+[\\.](?i)(docx|doc|pptx|ppt|xlsx|xls|rtf|eio|uof|uos|xml|pdf|ofd|jpeg|gif|jpg|png|txt|zip|rar|tif|properties|html|DOCX|DOC|PPTX|PPT|XLSX|XLS|RTF|EIO|UOF|UOS|XML|PDF|OFD|JPEG|GIF|JPG|PNG|TXT|ZIP|RAR|TIF|PROPERTIES|HTML)");

        try {
            new URLDecoder();
            Matcher mc = pat.matcher(URLDecoder.decode(str, "UTF-8"));
            if (mc.find()) {
                String filename = URLDecoder.decode(mc.group().substring(1), "utf-8");
                System.out.println(filename);
            } else {
                System.out.println("匹配失败");
            }
        } catch (UnsupportedEncodingException var14) {
            System.out.println("匹配失败");
            var14.printStackTrace();
        }

    }
}
