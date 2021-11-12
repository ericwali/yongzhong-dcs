//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.httpClient;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.service.file.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        urlPatterns = {"/delfile"},
        asyncSupported = true
)
public class DelInputServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DelInputServlet() {
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String[] filenametemp = request.getParameterValues("filename");
        String input = Config.inputDir + File.separator;
        String resultJson;
        int delNum;
        int i;
        if (filenametemp.length == 1 && filenametemp[0].equals("delAll")) {
            try {
                File allFile = new File("" + Config.inputDir);
                String[] children = allFile.list();
                delNum = children.length;

                for(i = 0; i < children.length; ++i) {
                    FileService.deleteDir(new File(allFile, children[i]));
                }
            } catch (Exception var14) {
                resultJson = JsonResultUtils.failJsonResult(ResultCode.E_DELETE_FAIL);
                out.write(resultJson);
                return;
            }

            resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_SUCCES.getValue(), "", "共删除" + delNum + "个文件");
            out.write(resultJson);
        } else {
            delNum = filenametemp.length;
            String[] var12 = filenametemp;
            int var11 = filenametemp.length;

            for(i = 0; i < var11; ++i) {
                String file = var12[i];
                file = input + file;

                try {
                    File f = new File(file);
                    if (!f.exists()) {
                        resultJson = JsonResultUtils.failJsonResult(ResultCode.E_INPUT_FILE_NOTFOUND);
                        out.write(resultJson);
                        return;
                    }

                    FileService.deleteDir(f);
                } catch (Exception var15) {
                    resultJson = JsonResultUtils.failJsonResult(ResultCode.E_DELETE_FAIL);
                    out.write(resultJson);
                    return;
                }
            }

            resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_SUCCES.getValue(), "", "共删除" + delNum + "个文件");
            out.write(resultJson);
        }
    }
}
