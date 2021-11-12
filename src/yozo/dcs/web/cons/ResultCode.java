//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.cons;

public enum ResultCode {
    E_SUCCES(0, "转换成功"),
    E_INPUT_FILE_NOTFOUND(1, "找不到指定文档"),
    E_INPUT_FILE_OPENFAILED(2, "无法打开指定文档"),
    E_CONVERSION_FAIL(3, "操作失败"),
    E_INPUT_FILE_ENCRPTED(4, "转换的文档为加密文档或密码有误，请重新添加password参数进行转换"),
    E_OUTPUT_FILE_WRONG_EXT(5, "输出文档后缀错误"),
    E_INVALID_PARAM(6, "无效参数"),
    E_CONVERSION_TIMEOUT(7, "转换超时，内容可能不完整"),
    E_UPLOAD_FILE(17, "上传失败"),
    E_URLDOWNLOAD_FILE(18, "下载文件失败"),
    E_UPLOAD_SIZE(19, "文件过大"),
    E_DOWNLOAD_SUCCESS(20, "下载成功"),
    E_DELETE_FAIL(21, "删除失败"),
    E_GETFIELPROP_FAIL(22, "获取文件信息失败"),
    E_ENCODURL_FAIL(23, "URL编码失败"),
    E_DESTINATIONNAME_ERROR(24, "生成文件名为空,有误或重名,请检查参数");

    private Integer value;
    private String info;

    private ResultCode(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getInfo() {
        return this.info;
    }

    public static ResultCode getEnum(Integer value) {
        ResultCode[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            ResultCode code = var4[var2];
            if (value == code.getValue()) {
                return code;
            }
        }

        return E_CONVERSION_FAIL;
    }
}
