//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.httpClient;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.service.httpclient.HttpClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(
        urlPatterns = {"/getfileprop"}
)
public class GetfilePropServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetfilePropServlet() {
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String resultJson = "";
        String url = request.getParameter("fileUrl");
        if (url != null && !"".equals(url)) {
            IResult<Object> result = HttpClientService.getfileprop(url);
            if (!result.isSuccess()) {
                resultJson = JsonResultUtils.failJsonResult(ResultCode.E_GETFIELPROP_FAIL);
            } else {
                Map<String, Object> map = (Map)result.getData();
                if (!map.isEmpty()) {
                    resultJson = JsonResultUtils.buildSuccessJsonResult(map);
                }
            }

            out.write(resultJson);
        } else {
            resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_INVALID_PARAM.getValue(), "", ResultCode.E_INVALID_PARAM.getInfo());
            out.write(resultJson);
        }
    }
}
