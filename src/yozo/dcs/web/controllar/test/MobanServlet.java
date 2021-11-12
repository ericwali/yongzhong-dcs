//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.test;

import yozo.dcs.commons.util.JsonResultUtils;
import yozo.dcs.web.bo.ConvertParam;
import yozo.dcs.web.bo.ConvertParamBuilder;
import yozo.dcs.web.cons.EnumConvertType;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.listener.Config;
import yozo.dcs.web.service.convert.ConvertService;
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
        name = "mbsc",
        urlPatterns = {"/mbsc/mbsc"}
)
public class MobanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String baseUrl;
    private static String baseContextPath;
    private String docUrl;

    public MobanServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startYear = request.getParameter("startYear");
        String startMonth = request.getParameter("startMonth");
        String startDay = request.getParameter("startDay");
        String fixedYear = request.getParameter("fixedYear");
        String yearMonth = request.getParameter("yearMonth");
        String hasTrialPeriod = request.getParameter("hasTrialPeriod");
        String trialMonth = request.getParameter("trialMonth");
        String job = request.getParameter("job");
        String jobDuty = request.getParameter("jobDuty");
        String jobLoc = request.getParameter("jobLoc");
        String hasSalaryType = request.getParameter("hasSalaryType");
        String salary = request.getParameter("salary");
        String salaryType1 = request.getParameter("salaryType1");
        String salary1 = request.getParameter("salary1");
        String salaryType2 = request.getParameter("salaryType2");
        String salary2 = request.getParameter("salary2");
        String salaryType3 = request.getParameter("salaryType3");
        String salary3 = request.getParameter("salary3");
        String hasDiffTrial = request.getParameter("hasDiffTrial");
        String hasTrialSalaryType = request.getParameter("hasTrialSalaryType");
        String trialSalary = request.getParameter("trialSalary");
        String trialSalaryType1 = request.getParameter("trialSalaryType1");
        String trialSalary1 = request.getParameter("trialSalary1");
        String trialSalaryType2 = request.getParameter("trialSalaryType2");
        String trialSalary2 = request.getParameter("trialSalary2");
        String trialSalaryType3 = request.getParameter("trialSalaryType3");
        String trialSalary3 = request.getParameter("trialSalary3");
        String salaryDay = request.getParameter("salaryDay");
        String salaryMonth = request.getParameter("salaryMonth");
        String hasBonusDesc = request.getParameter("hasBonusDesc");
        String bonusDesc = request.getParameter("bonusDesc");
        String hasBenefits = request.getParameter("hasBenefits");
        String benefits = request.getParameter("benefits");
        String hasInvalidDesc = request.getParameter("hasInvalidDesc");
        String otherInvalid = request.getParameter("otherInvalid");
        String hasFakeDesc = request.getParameter("hasFakeDesc");
        String otherFakeDesc = request.getParameter("otherFakeDesc");
        String hasOtherDesc = request.getParameter("hasOtherDesc");
        String otherDesc = request.getParameter("otherDesc");
        String corpName = request.getParameter("corpName");
        String corpAddr = request.getParameter("corpAddr");
        String corpManager = request.getParameter("corpManager");
        String laborName = request.getParameter("laborName");
        String laborSsn = request.getParameter("laborSsn");
        String laborAddr = request.getParameter("laborAddr");
        PrintWriter out = response.getWriter();
        String resultJson = null;
        ConvertParam param = (ConvertParam)request.getAttribute("param");
        if (param == null) {
            param = (new ConvertParamBuilder()).buildParam(request);
        }

        List<String> list = new ArrayList();
        list.add("corpName");
        list.add(corpName);
        list.add("corpName1");
        list.add(corpName);
        list.add("corpAddr");
        list.add(corpAddr);
        list.add("corpManager");
        list.add(corpManager);
        list.add("laborName");
        list.add(laborName);
        list.add("laborSsn");
        list.add(laborSsn);
        list.add("laborAddr");
        list.add(laborAddr);
        list.add("startYear");
        list.add(startYear);
        list.add("startMonth");
        list.add(startMonth);
        list.add("startDay");
        list.add(startDay);
        list.add("fixedYear");
        list.add(fixedYear);
        list.add("yearMonth");
        list.add(yearMonth);
        if (hasTrialPeriod.equals("1")) {
            list.add("hasTrialPeriod");
            list.add("设有");
            list.add("trialMonth");
            list.add("，试用期为首" + trialMonth + "个月");
        } else {
            list.add("hasTrialPeriod");
            list.add("不设");
        }

        list.add("job");
        list.add(job);
        list.add("jobDuty");
        list.add(jobDuty);
        list.add("jobLoc");
        list.add(jobLoc);
        if (hasSalaryType.equals("0")) {
            list.add("hasSalaryType");
            list.add("按甲方现行工资制度确认乙方工资在试用期结束后为" + salary + "元（税前）。");
        } else {
            list.add("hasSalaryType");
            list.add("按甲方现行工资制度确认乙方工资在试用期结束后为:");
            if (!salaryType1.isEmpty() && !salary1.isEmpty()) {
                list.add("salaryType1");
                list.add(salaryType1 + ":");
                list.add("salary1");
                list.add(salary1 + ";");
            } else {
                System.out.println("数据为空");
            }

            if (!salaryType2.isEmpty() && !salary2.isEmpty()) {
                list.add("salaryType2");
                list.add(salaryType2 + ":");
                list.add("salary2");
                list.add(salary2 + ";");
            } else {
                System.out.println("数据为空");
            }

            if (!salaryType3.isEmpty() && !salary3.isEmpty()) {
                list.add("salaryType3");
                list.add(salaryType3 + ":");
                list.add("salary3");
                list.add(salary3 + ";");
            } else {
                System.out.println("数据为空");
            }
        }

        if (hasDiffTrial.equals("0")) {
            list.add("hasDiffTrial");
            list.add("乙方在试用期内工资与转正后相同。");
        } else if (hasTrialSalaryType.equals("0")) {
            list.add("hasDiffTrial");
            list.add("乙方在试用期内工资为" + trialSalary + "元（税前）。");
        } else {
            list.add("hasDiffTrial");
            list.add("乙方在试用期内工资拆分为:");
            if (!trialSalaryType1.isEmpty() && !trialSalary1.isEmpty()) {
                list.add("trialSalaryType1");
                list.add(trialSalaryType1 + ":");
                list.add("trialSalary1");
                list.add(trialSalary1 + ";");
            } else {
                System.out.println("数据为空");
            }

            if (!trialSalaryType2.isEmpty() && !trialSalary2.isEmpty()) {
                list.add("trialSalaryType2");
                list.add(trialSalaryType2 + ":");
                list.add("trialSalary2");
                list.add(trialSalary2 + ";");
            } else {
                System.out.println("数据为空");
            }

            if (!trialSalaryType3.isEmpty() && !trialSalary3.isEmpty()) {
                list.add("trialSalaryType3");
                list.add(trialSalaryType3 + ":");
                list.add("trialSalary3");
                list.add(trialSalary3 + ";");
            } else {
                System.out.println("数据为空");
            }
        }

        list.add("salaryDay");
        list.add(salaryDay);
        list.add("salaryMonth");
        list.add(salaryMonth);
        if (hasBonusDesc.equals("1")) {
            list.add("hasBonusDesc");
            list.add("奖金计算方式如下：" + bonusDesc);
        }

        if (hasBenefits.equals("1")) {
            list.add("hasBenefits");
            list.add("3.甲方还应为乙方提供以下福利待遇：" + benefits);
        }

        if (hasOtherDesc.equals("0")) {
            list.add("hasOtherDesc");
            list.add("暂无");
        } else {
            list.add("hasOtherDesc");
            list.add("双方约定如下：" + otherDesc);
        }

        String[] bookMark = (String[])list.toArray(new String[list.size()]);
        param.setBookMark(bookMark);
        String outputtemp = param.getOutput();
        String output = outputtemp.substring(0, outputtemp.lastIndexOf("."));
        String docname = output.substring(output.lastIndexOf(File.separator) + 1);
        String sourceFileName = param.getInput();
        String targetFileName = output + ".doc";
        this.docUrl = Config.staticPath + docname + ".doc";
        ConvertService.getInstance().convertdoc(sourceFileName, targetFileName, param);
        IResult<Integer> re = ConvertService.getInstance().convert(param);
        resultJson = this.getJsonResult(request, re, param);
        out.write(resultJson);
        out.flush();
    }

    private String getJsonResult(HttpServletRequest request, IResult<Integer> re, ConvertParam param) {
        String resultJson = null;
        if (re.isSuccess()) {
            Map<String, Object> result = new HashMap();
            if (param.getIsSourceDir() == 1) {
                result.put("sourceDir", param.getInput().replace(Config.inputDir.toString(), ""));
            }

            if (!EnumConvertType.GET_PAGECONUT_PDF.getType().equals(param.getType()) && !EnumConvertType.GET_PAGECONUT_MS.getType().equals(param.getType())) {
                List<String> resultDate = this.getResultDate(param, request);
                if (!resultDate.isEmpty()) {
                    result.put("data", resultDate);
                    result.put("docUrl", this.docUrl);
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

                    resultJson = JsonResultUtils.buildSuccessJsonResult(result);
                } else {
                    resultJson = JsonResultUtils.failJsonResult(ResultCode.E_CONVERSION_FAIL);
                }
            } else {
                result.put("pagecount", re.getData());
                resultJson = JsonResultUtils.buildSuccessJsonResult(result);
            }
        } else {
            Integer code = (Integer)re.getData();
            ResultCode resultCode = ResultCode.getEnum(code);
            resultJson = JsonResultUtils.failJsonResult(resultCode);
        }

        return resultJson;
    }

    private List<String> getResultDate(ConvertParam param, HttpServletRequest request) {
        if (baseUrl == null) {
            String fullUrl = request.getRequestURL().toString();
            if (!fullUrl.contains("convert")) {
                (new StringBuilder("http://")).append(request.getHeader("Host")).toString();
            } else {
                baseUrl = fullUrl.substring(0, fullUrl.lastIndexOf("/convert"));
                baseContextPath = request.getContextPath();
            }
        }

        List<String> result = new ArrayList();
        File file = new File(param.getOutput() == null ? "" : param.getOutput());
        if (file.exists()) {
            if (EnumConvertType.isTemplet(param.getType())) {
                String path;
                String paramUrl;
                switch(EnumConvertType.getEnum(EnumConvertType.getEnumByType(param.getType()).ordinal()).getValue()) {
                    case 17:
                        try {
                            paramUrl = URLEncoder.encode(param.getFileName(), "utf-8");
                        } catch (Exception var10) {
                            paramUrl = "newFile";
                        }

                        path = baseUrl + "/view.html?" + param.getTargetName() + "==" + paramUrl;
                        result.add(path);
                    case 18:
                    case 19:
                    default:
                        break;
                    case 20:
                        path = baseUrl + "/zipview.html?" + param.getTargetImageFolder();
                        result.add(path);
                        break;
                    case 21:
                        paramUrl = baseContextPath + "/pdfview?filePath=" + param.getTargetImageFolder();

                        try {
                            path = baseUrl + "/pdf/generic/web/viewer.html?file=" + URLEncoder.encode(paramUrl, "UTF-8");
                        } catch (UnsupportedEncodingException var11) {
                            path = "解析失败";
                        }

                        result.add(path);
                        break;
                    case 22:
                        paramUrl = baseContextPath + "/pdfview?fileName=" + param.getFileName() + "&filePath=" + param.getTargetImageFolder();
                        path = baseUrl + "/ofd/index.html?" + paramUrl;
                        result.add(path);
                }
            } else if (EnumConvertType.isImageTarget(param.getType())) {
                File[] tempList = file.listFiles();
                if (tempList != null) {
                    File[] var9 = tempList;
                    int var8 = tempList.length;

                    for(int var7 = 0; var7 < var8; ++var7) {
                        File temp = var9[var7];
                        if (temp.isFile()) {
                            result.add(Config.staticPath + param.getTargetName() + "/" + temp.getName());
                        }
                    }
                }
            } else if (file.exists()) {
                result.add(Config.staticPath + param.getTargetName());
            }
        }

        return result;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
