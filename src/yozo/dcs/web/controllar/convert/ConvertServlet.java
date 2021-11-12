//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.convert;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.bo.ConvertParam;
import yozo.dcs.web.bo.ConvertParamBuilder;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.service.convert.ConvertService;
import yozo.dcs.web.service.file.FileService;
import yozo.dcs.web.service.zip.ZipService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(
        urlPatterns = {"/convert"}
)
public class ConvertServlet extends HttpServlet {
    private static String staticpath;

    public ConvertServlet() {
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String resultJson = null;
        ConvertParam param = (new ConvertParamBuilder()).buildParam(request);
        IResult<ResultCode> paramRe = param.checkParam(request);
        if (!paramRe.isSuccess()) {
            resultJson = JsonResultUtils.failJsonResult((ResultCode)paramRe.getData());
        } else {
            IResult<Integer> re = ConvertService.getInstance().convert(param);
            resultJson = this.getJsonResult(request, re, param);
        }

        if (param.getCallbackName() != null && !"".equals(param.getCallbackName())) {
            resultJson = param.getCallbackName() + "(" + resultJson + ");";
        }

        if (param.getIsDelSrc() == 1) {
            FileService.deleteSource(param.getInput());
        }

        if (param.getIsDelTarget() == 1 && param.getIsZip() == 1) {
            FileService.deleteTarget(param.getOutput());
        }

        out.write(resultJson);
        out.flush();
    }

    private String getJsonResult(HttpServletRequest request, IResult<Integer> re, ConvertParam param) {
        String resultJson = null;
        if (re.isSuccess()) {
            Map<String, Object> result = new HashMap();
            result.put("result", ResultCode.E_SUCCES.getValue());
            result.put("message", re.getMessage());
            if (EnumConvertType.getTargetValue(param.getType()) != null) {
                result.put("type", EnumConvertType.getTargetValue(param.getType()));
            } else {
                result.put("type", "没有找到该转换类型");
            }

            String relativePath = request.getParameter("relativePath");
            String path = param.getRelativePath();
            if ("1".equals(relativePath)) {
                result.put("relativePath", path);
            }

            if (param.getIsSourceDir() == 1) {
                result.put("sourceDir", param.getInput().replace(Config.inputDir.toString(), ""));
            }

            if (!EnumConvertType.GET_PAGECONUT_PDF.getType().equals(param.getType()) && !EnumConvertType.GET_PAGECONUT_MS.getType().equals(param.getType())) {
                List<String> resultDate = this.getResultDate(param, request);
                if (!resultDate.isEmpty()) {
                    if (param.getIsDelTarget() == 1 && param.getIsZip() == 1) {
                        result.put("data", "生成文件已删除");
                    } else {
                        result.put("data", resultDate);
                    }

                    if (param.getIsSourceDir() == 1) {
                        result.put("sourceDir", param.getInput().replace(Config.inputDir.toString(), ""));
                    }

                    if (param.getIsZip() == 1) {
                        IResult<String> zipResult = ZipService.zipFile(param);
                        if (zipResult.isSuccess()) {
                            result.put("zipUrl", Config.staticPath + "/" + (String)zipResult.getData());
                        } else {
                            result.put("zipUrl", "");
                        }
                    }

                    if (EnumConvertType.isResultPage(param.getType())) {
                        result.put("pagecount", re.getData());
                    }

                    resultJson = JsonResultUtils.buildJsonResultByMap(result);
                } else {
                    resultJson = JsonResultUtils.failJsonResult(ResultCode.E_CONVERSION_FAIL);
                }
            } else {
                result.put("pagecount", re.getData());
                resultJson = JsonResultUtils.buildJsonResultByMap(result);
            }
        } else {
            Integer code = (Integer)re.getData();
            ResultCode resultCode = ResultCode.getEnum(code);
            resultJson = JsonResultUtils.failJsonResult(resultCode);
        }

        return resultJson;
    }

    private String getbaseContextPath(String domain) {
        return domain.lastIndexOf("/") > -1 ? domain.substring(domain.lastIndexOf("/")) : "";
    }

    private List<String> getResultDate(ConvertParam param, HttpServletRequest request) {
        String dcshost = request.getHeader("Dcshost");
        String baseUrl;
        String baseContextPath;
        String tempstaticpath;
        if (Config.domain != null && !"".equals(Config.domain)) {
            baseUrl = "http://" + Config.domain;
            baseContextPath = this.getbaseContextPath(Config.domain);
        } else if (dcshost != null && !"".equals(dcshost)) {
            baseUrl = "http://" + dcshost;
            baseContextPath = this.getbaseContextPath(dcshost);
        } else {
            tempstaticpath = request.getRequestURL().toString();
            baseUrl = tempstaticpath.substring(0, tempstaticpath.lastIndexOf("/convert"));
            baseContextPath = request.getContextPath();
        }

        if (staticpath == null && Config.staticPath.startsWith("./")) {
            tempstaticpath = Config.staticPath;
            String tempstr = Config.staticPath.substring(Config.staticPath.lastIndexOf("./") + 1);
            staticpath = baseUrl + tempstr;
            Config.staticPath = staticpath;
            if (Config.pdfStaticPath.equals(tempstaticpath)) {
                Config.pdfStaticPath = Config.staticPath;
            }
        }

        List<String> result = new ArrayList();
        File file = new File(param.getOutput() == null ? "" : param.getOutput());
        if (file.exists()) {
            if (EnumConvertType.isTemplet(param.getType())) {
                String path;
                String filename;
                switch(EnumConvertType.getEnum(EnumConvertType.getEnumByType(param.getType()).ordinal()).getValue()) {
                    case 17:
                        try {
                            filename = URLEncoder.encode(param.getFileName(), "utf-8");
                        } catch (Exception var13) {
                            filename = "newFile";
                        }

                        path = baseUrl + "/view.html?" + param.getRelativePath() + "==" + filename;
                        result.add(path);
                    case 18:
                    case 19:
                    default:
                        break;
                    case 20:
                        path = baseUrl + "/zipview.html?" + param.getRelativePath();
                        result.add(path);
                        break;
                    case 21:
                        try {
                            path = baseUrl + "/pdf/generic/web/viewer.html?file=" + URLEncoder.encode(Config.pdfStaticPath + param.getRelativePath() + "/" + param.getFileName().replaceAll(" ", ""), "UTF-8");
                        } catch (UnsupportedEncodingException var14) {
                            path = "解析失败";
                        }

                        result.add(path);
                        break;
                    case 22:
                        filename = baseContextPath + "/pdfview?filePath=" + param.getRelativePath();
                        path = baseUrl + "/ofd/index.html?file=" + filename;
                        result.add(path);
                }
            } else if (EnumConvertType.isImageTarget(param.getType())) {
                File[] tempList = file.listFiles();
                if (tempList != null) {
                    File[] var12 = tempList;
                    int var11 = tempList.length;

                    for(int var10 = 0; var10 < var11; ++var10) {
                        File temp = var12[var10];
                        if (temp.isFile()) {
                            result.add(Config.staticPath + param.getRelativePath() + "/" + temp.getName());
                        }
                    }
                }
            } else if (file.exists()) {
                result.add(Config.staticPath + param.getRelativePath() + "/" + param.getTargetName());
            }
        }

        return result;
    }
}
