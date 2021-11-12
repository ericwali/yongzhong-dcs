//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.show;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.service.show.TempShowService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        urlPatterns = {"/zipview"}
)
public class ZipViewServlet extends HttpServlet {
    public ZipViewServlet() {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String relativePath = Config.outputDir + File.separator + request.getParameter("fileName");
        relativePath = relativePath.replaceAll("\\.\\.", "");
        String staticPath = Config.staticPath + "/" + request.getParameter("fileName").replaceAll("\\.\\.", "") + "/";
        Boolean bool = this.isCorrectFile(relativePath);
        String resultJson;
        if (bool) {
            IResult<Object> result = TempShowService.getZipFileJson(relativePath);
            if (result.isSuccess()) {
                Map<String, Object> resultMap = new HashMap();
                resultMap.put("foldJson", result.getData());
                resultMap.put("staticPath", staticPath);
                resultJson = JsonResultUtils.buildSuccessJsonResult(resultMap);
            } else {
                resultJson = JsonResultUtils.failJsonResult(ResultCode.E_INPUT_FILE_NOTFOUND);
            }
        } else {
            resultJson = JsonResultUtils.failJsonResult(ResultCode.E_INPUT_FILE_OPENFAILED);
        }

        out.write(resultJson);
        out.flush();
    }

    private Boolean isCorrectFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            String[] files = file.list();
            String[] var7 = files;
            int var6 = files.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                String filename = var7[var5];
                if (filename.toLowerCase().matches(".*(zip|rar|7z|gz)")) {
                    String filename2 = filename.substring(0, filename.lastIndexOf("."));

                    for(int i = 0; i < files.length; ++i) {
                        if (files[i].equals(filename2)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}
