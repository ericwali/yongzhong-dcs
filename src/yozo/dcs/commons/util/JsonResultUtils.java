//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import yozo.dcs.web.cons.ResultCode;

import java.util.HashMap;
import java.util.Map;

public class JsonResultUtils {
    private static ThreadLocal<ObjectMapper> mapperHolder = new ThreadLocal();

    public JsonResultUtils() {
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = (ObjectMapper)mapperHolder.get();
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapperHolder.set(mapper);
            return mapper;
        } else {
            return mapper;
        }
    }

    public static String success() {
        return success((Object)null, (String)null);
    }

    public static String success(Object data) {
        return success(data, (String)null);
    }

    public static String success(Object data, String message) {
        return buildJsonResult(ResultCode.E_SUCCES.getValue(), data, message);
    }

    public static String failJsonResult(ResultCode code) {
        Map<String, Object> params = new HashMap();
        params.put("result", code.getValue());
        params.put("message", code.getInfo());
        params.put("data", "");

        try {
            return getObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public static String buildJsonResult(Integer code, Object data, String message) {
        Map<String, Object> params = new HashMap();
        params.put("result", code);
        params.put("message", message);
        params.put("data", data == null ? "" : data);

        try {
            return getObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException var5) {
            var5.printStackTrace();
            return "";
        }
    }

    public static String buildJsonResultByMap(Map<String, Object> map) {
        try {
            return getObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String buildSuccessJsonResult(Map<String, Object> map) {
        map.put("result", ResultCode.E_SUCCES.getValue());
        map.put("message", "操作成功");

        try {
            return getObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String buildFailJsonResult(Map<String, Object> map) {
        map.put("result", ResultCode.E_CONVERSION_FAIL.getValue());
        map.put("message", "操作失败");

        try {
            return getObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(success("dddd"));
    }
}
