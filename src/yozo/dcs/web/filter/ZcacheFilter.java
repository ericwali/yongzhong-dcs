//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.filter;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

@WebFilter(
        filterName = "zcacheF",
        urlPatterns = {"/*"}
)
public class ZcacheFilter implements Filter {
    private static final String output;

    static {
        output = Config.outputDir.toString();
    }

    public ZcacheFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest)request;
        String enablecache = httprequest.getParameter("enableCache");
        if ("1".equals(enablecache)) {
            String reloutdir = httprequest.getParameter("reloutDir");
            if (reloutdir != null && !"".equals(reloutdir)) {
                reloutdir = reloutdir.replaceAll("(\\\\|/)", Matcher.quoteReplacement(File.separator));
                String filepath = output + File.separator + reloutdir;
                Boolean bool = searchFiles(filepath);
                if (bool) {
                    resultjson(request, response, reloutdir);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public static Boolean searchFiles(String filepath) {
        File file = new File(filepath);
        return file.exists() && !file.isDirectory() ? true : false;
    }

    public static void resultjson(ServletRequest request, ServletResponse response, String reloutdir) throws IOException {
        PrintWriter out = response.getWriter();
        String resultJson = null;
        Map<String, Object> result = new HashMap();
        List<String> resultdata = new ArrayList();
        resultdata.add(Config.staticPath + reloutdir.replaceAll("\\\\", "/"));
        result.put("result", ResultCode.E_SUCCES.getValue());
        result.put("message", "读取缓存文件");
        result.put("data", resultdata);
        resultJson = JsonResultUtils.buildJsonResultByMap(result);
        out.write(resultJson);
        out.flush();
    }
}
