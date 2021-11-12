//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.httpClient;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.service.httpclient.HttpClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(
        urlPatterns = {"/onlinefile"},
        asyncSupported = true
)
public class UrlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UrlServlet() {

    }
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String url = request.getParameter("downloadUrl");
        String resultJson;
        if (url == null) {
            resultJson = JsonResultUtils.failJsonResult(ResultCode.E_INVALID_PARAM);
            out.write(resultJson);
        } else {
            UUID uuid = UUID.randomUUID();
            String input = Config.inputDir.getPath() + File.separator + uuid + File.separator;
            IResult<String> result = HttpClientService.download(url, input, "");
            if (!result.isSuccess()) {
                resultJson = JsonResultUtils.failJsonResult(ResultCode.E_URLDOWNLOAD_FILE);
                if (request.getParameter("callbackName") != null && !"".equals(request.getParameter("callbackName"))) {
                    resultJson = request.getParameter("callbackName") + "(" + resultJson + ");";
                }

                out.write(resultJson);
            } else {
                String filename = (String)result.getData();
                input = input + filename;
                request.setAttribute("fname", filename);
                request.setAttribute("finput", input);
                request.getRequestDispatcher("/convert").forward(request, response);
            }
        }
    }
}
