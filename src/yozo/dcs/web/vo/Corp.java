//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.vo;

public abstract class Corp {
    private String name = "";
    private String position = "";
    private String size = "";

    public Corp(String _name, String _position, String _size) {
        this.name = _name;
        this.position = _position;
        this.size = _size;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
