//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.test;

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
        name = "testUpload",
        urlPatterns = {"/testUpload"}
)
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UploadServlet() {
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        UUID uuid = UUID.randomUUID();
        String resultJson = null;
        Part part = request.getPart("file");
        if (part.getSize() > Config.uploadSize) {
            resultJson = JsonResultUtils.failJsonResult(ResultCode.E_UPLOAD_SIZE);
            out.write(resultJson);
        } else {
            String header = part.getHeader("content-disposition");
            String filename = this.getFileName(header);
            String input = Config.inputDir.getPath() + File.separator + uuid + File.separator + filename;
            String returnDir = uuid + File.separator + filename;
            boolean result = FileService.inputStreamToFile(part.getInputStream(), input);
            if (!result) {
                resultJson = JsonResultUtils.failJsonResult(ResultCode.E_UPLOAD_FILE);
            } else {
                resultJson = JsonResultUtils.buildJsonResult(ResultCode.E_SUCCES.getValue(), returnDir, ResultCode.E_SUCCES.getInfo());
            }

            out.write(resultJson);
        }
    }

    public String getFileName(String header) {
        String[] tempArr1 = header.split(";");
        String[] tempArr2 = tempArr1[2].split("=");
        String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
        return fileName;
    }
}
