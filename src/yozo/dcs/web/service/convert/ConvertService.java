//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.convert;

import yozo.dcs.commons.util.DateViewUtils;
import yozo.dcs.web.bo.ConvertInfoBO;
import yozo.dcs.web.bo.ConvertParam;
import yozo.dcs.web.cons.DefaultResult;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.cons.ResultCode;
import yozo.dcs.web.log.LogUtil;

import java.util.Date;

public class ConvertService {
    private static final ConvertService convertService = new ConvertService();

    private ConvertService() {
    }

    public static ConvertService getInstance() {
        return convertService;
    }

    public static void main(String[] args) {
        ConvertInstance instance = new ConvertInstance(1);
        System.out.println(instance.queryDays());
        System.out.println(instance.queryTimes());
    }

    public IResult<ConvertInfoBO> queryInfo() {
        ConvertInstance convertInstance = ConvertManager.instance.get();

        DefaultResult var6;
        try {
            int days = convertInstance.queryDays();
            int times = convertInstance.queryTimes();
            ConvertInfoBO convertInfo = new ConvertInfoBO();
            convertInfo.setActivatDays(days < 0 ? "-" : String.valueOf(days));
            convertInfo.setActivatTimes(times < 0 ? "-" : String.valueOf(times));
            var6 = DefaultResult.successResult(convertInfo);
            return var6;
        } catch (Exception var9) {
            var6 = DefaultResult.failResult("查询失败", (Object)null);
        } finally {
            ConvertManager.instance.free(convertInstance);
        }

        return var6;
    }

    public IResult<Integer> convert(ConvertParam param) {
        ConvertInstance convertInstance = ConvertManager.instance.get();
        long startTime = System.currentTimeMillis();

        Object resutl;
        try {
            resutl = convertInstance.convert(param);
        } catch (Exception var10) {
            LogUtil.error(var10);
            resutl = DefaultResult.failResult();
        } finally {
            ConvertManager.instance.free(convertInstance);
        }

        double spendTime = (double)(System.currentTimeMillis() - startTime) / 1000.0D;
        if (((IResult)resutl).isSuccess()) {
            LogUtil.info(DateViewUtils.formatFullDate(new Date()) + "success source file:" + param.getInput() + " target file:" + param.getOutput() + " spendTime:" + spendTime + "s" + LogUtil.lineSep);
            return DefaultResult.successResult(((IResult)resutl).getMessage(), (Integer)((IResult)resutl).getData());
        } else {
            LogUtil.info("========================================" + LogUtil.lineSep + "========================================" + LogUtil.lineSep + DateViewUtils.formatFullDate(new Date()) + "failuer source file:" + param.getInput() + " Failure reason:" + ResultCode.getEnum((Integer)((IResult)resutl).getData()).getInfo() + LogUtil.lineSep);
            return DefaultResult.failResult(((IResult)resutl).getMessage(), (Integer)((IResult)resutl).getData());
        }
    }

    public void convertdoc(String inFile, String outFile, ConvertParam param) {
        ConvertInstance convertInstance = ConvertManager.instance.get();

        try {
            convertInstance.convertdoc(inFile, outFile, param);
        } catch (Exception var9) {
            LogUtil.error(var9);
        } finally {
            ConvertManager.instance.free(convertInstance);
        }

    }
}
