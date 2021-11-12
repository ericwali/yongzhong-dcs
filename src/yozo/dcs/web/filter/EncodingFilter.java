//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.filter;

import yozo.dcs.web.listener.Config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "encodingF",
        urlPatterns = {"/*"},
        initParams = {@WebInitParam(
                name = "Encoding",
                value = "UTF-8"
        ), @WebInitParam(
                name = "enabled",
                value = "true"
        )}
)
public class EncodingFilter implements Filter {
    private String Encoding;
    private boolean enabled;

    public EncodingFilter() {
    }

    public void init(FilterConfig config) throws ServletException {
        this.Encoding = config.getInitParameter("Encoding");
        this.enabled = "true".equalsIgnoreCase(this.Encoding.trim()) || "1".equalsIgnoreCase(this.Encoding.trim());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse)response;
        if (this.enabled || this.Encoding != null) {
            request.setCharacterEncoding(this.Encoding);
            response.setContentType("text/html;charset=" + this.Encoding + "\"");
            response.setCharacterEncoding(this.Encoding);
        }

        if (Config.supportCross == null || Config.supportCross.equals("true")) {
            resp.addHeader("Access-Control-Allow-Origin", "*");
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        this.Encoding = null;
    }
}
