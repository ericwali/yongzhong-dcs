//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.vo;

import java.util.ArrayList;

public class BranchFolder extends Corp {
    ArrayList<Corp> children = new ArrayList();

    public BranchFolder(String _name, String _position, String _size) {
        super(_name, _position, _size);
    }

    public void addChildren(Corp corp) {
        this.children.add(corp);
    }

    public ArrayList<Corp> getChildren() {
        return this.children;
    }
}
