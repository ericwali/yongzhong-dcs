//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.queryInfo;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.service.convert.ConvertManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        urlPatterns = {"/countConverter"}
)
public class CountConverterServlet extends HttpServlet {
    public CountConverterServlet() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String resultJson = JsonResultUtils.success(ConvertManager.instance.Size(), "操作成功");
        out.write(resultJson);
        out.flush();
    }
}
