//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.cons;

import java.awt.Color;

public enum EnumColor {
    BLACK(1, "黑色", Color.black),
    BLUE(2, "蓝色", Color.blue),
    CYAN(3, "青色", Color.cyan),
    DARKGRAY(4, "深灰", Color.darkGray),
    GRAY(5, "灰色", Color.gray),
    GREEN(6, "绿色", Color.green),
    LIGHTGRAY(7, "浅灰", Color.lightGray),
    MAGENTA(8, "品红", Color.MAGENTA),
    ORANGE(9, "橙色", Color.ORANGE),
    PINK(10, "粉红", Color.PINK),
    RED(11, "红色", Color.RED),
    WHITE(12, "白色", Color.WHITE),
    YELLOW(13, "黄色", Color.YELLOW);

    private Integer code;
    private String info;
    private Color color;

    public static Color getColor(Integer code) {
        EnumColor[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            EnumColor color = var4[var2];
            if (code == color.getCode()) {
                return color.getColor();
            }
        }

        return GRAY.getColor();
    }

    private EnumColor(Integer code, String info, Color color) {
        this.code = code;
        this.info = info;
        this.color = color;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
