//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.queryInfo;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.bo.ConvertInfoBO;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.service.convert.ConvertService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        urlPatterns = {"/queryInfo"}
)
public class QueryServlet extends HttpServlet {
    private static final String version = "DCS3.0.22_6.1.1454.101ZH.S1";

    public QueryServlet() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        ConvertService convertService = ConvertService.getInstance();
        IResult<ConvertInfoBO> result = convertService.queryInfo();
        String resultJson;
        if (result.isSuccess()) {
            ConvertInfoBO info = (ConvertInfoBO)result.getData();
            info.setVersion("DCS3.0.22_6.1.1454.101ZH.S1");
            resultJson = JsonResultUtils.success(info, "操作成功");
        } else {
            resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_INVALID_PARAM.getValue(), "", result.getMessage());
        }

        out.write(resultJson);
        out.flush();
    }
}
