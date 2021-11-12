//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.show;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(
        urlPatterns = {"/view"}
)
public class DocViewServlet extends HttpServlet {
    public DocViewServlet() {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        List<String> result = new ArrayList();
        String relativePath = request.getParameter("fileName");
        File file = new File(Config.outputDir + File.separator + relativePath);
        String staticPath = Config.staticPath;
        String resultJson;
        if (file.exists()) {
            String[] namelist = file.list();
            List<String> temp = Arrays.asList(namelist);
            Collections.sort(temp, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    int n1 = Integer.parseInt(o1.substring(0, o1.indexOf(".")));
                    int n2 = Integer.parseInt(o2.substring(0, o2.indexOf(".")));
                    return n1 - n2;
                }
            });
            Iterator var12 = temp.iterator();

            while(var12.hasNext()) {
                String name = (String)var12.next();
                if (name.endsWith(".svg")) {
                    result.add(staticPath + "/" + relativePath + "/" + name);
                }
            }

            resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_SUCCES.getValue(), result, ResultCode.E_SUCCES.getInfo());
        } else {
            resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_INVALID_PARAM.getValue(), (Object)null, ResultCode.E_INVALID_PARAM.getInfo());
        }

        out.write(resultJson);
        out.flush();
    }
}
