//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.upload;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.service.file.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(
        name = "upload",
        urlPatterns = {"/upload"},
        asyncSupported = true
)
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UploadServlet() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        Part part;
        try {
            part = request.getPart("file");
        } catch (Exception var10) {
            part = null;
        }

        String resultJson;
        if (part == null) {
            resultJson = JsonResultUtils.failJsonResult(ResultCode.E_INVALID_PARAM);
            out.write(resultJson);
        } else {
            String filename = part.getSubmittedFileName();
            UUID uuid = UUID.randomUUID();
            String input = Config.inputDir.getPath() + File.separator + uuid + File.separator + filename;
            boolean result = FileService.inputStreamCopyToFile(part.getInputStream(), input);
            if (!result) {
                resultJson = JsonResultUtils.failJsonResult(ResultCode.E_UPLOAD_FILE);
                out.write(resultJson);
            } else {
                request.setAttribute("fname", filename);
                request.setAttribute("finput", input);
                request.getRequestDispatcher("/convert").forward(request, response);
            }
        }
    }
}
