//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.upload;

import sun.misc.BASE64Encoder;
import yozo.dcs.commons.util.DateViewUtils;
import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.bo.ConvertParam;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.cons.IResult;
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
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@WebServlet(
        name = "weiXinShareUpload",
        urlPatterns = {"/shareUpload"}
)
@MultipartConfig
public class WXUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WXUploadServlet() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        UUID uuid = UUID.randomUUID();
        String convertTypeParame = request.getParameter("convertType");
        IResult<EnumConvertType> checkParame = EnumConvertType.checkParame(convertTypeParame);
        Part part = request.getPart("file");
        String filename;
        if (checkParame.isSuccess() && part != null) {
            if (part.getSize() > Config.uploadSize) {
                filename = JsonResultUtils.buildJsonResult(ResultCode.E_UPLOAD_SIZE.getValue(), "", ResultCode.E_UPLOAD_SIZE.getInfo());
                out.write(filename);
            } else {
                filename = part.getSubmittedFileName();
                String targetType = ((EnumConvertType)checkParame.getData()).getExt();
                String tempName = this.getName();
                String targetName = tempName + "." + targetType;
                String output = Config.outputDir.getPath() + File.separator + targetName;
                String input = Config.inputDir.getPath() + File.separator + uuid + File.separator + filename;
                new FileService();
                boolean result = FileService.inputStreamToFile(part.getInputStream(), input);
                String startPageP;
                if (!result) {
                    startPageP = JsonResultUtils.buildJsonResult(ResultCode.E_UPLOAD_FILE.getValue(), (Object)null, ResultCode.E_UPLOAD_FILE.getInfo());
                    out.write(startPageP);
                } else {
                    startPageP = request.getParameter("startPage");
                    String endPageP = request.getParameter("endPage");
                    boolean var10000;
                    if (startPageP == null) {
                        var10000 = false;
                    } else {
                        Integer.parseInt(startPageP);
                    }

                    if (endPageP == null) {
                        var10000 = false;
                    } else {
                        Integer.parseInt(endPageP);
                    }

                    ConvertParam param = new ConvertParam(targetName, filename, ((EnumConvertType)checkParame.getData()).getType(), input, output);
                    if (EnumConvertType.isImageTarget(param.getType())) {
                        param.setTargetImageFolder(Config.outputDir.getPath() + File.separator + tempName);
                        param.setOutput(param.getTargetImageFolder());
                        param.setTargetName(tempName);
                    }

                    request.setAttribute("param", param);
                    request.getRequestDispatcher("/weiXinShare").forward(request, response);
                }
            }
        } else {
            filename = JsonResultUtils.buildJsonResult(ResultCode.E_INVALID_PARAM.getValue(), "", ResultCode.E_INVALID_PARAM.getInfo());
            out.write(filename);
        }
    }

    private String getName() {
        return (new BASE64Encoder()).encode((DateViewUtils.formatFullDate(new Date()) + (new Random(System.currentTimeMillis())).nextInt(100)).getBytes());
    }
}
