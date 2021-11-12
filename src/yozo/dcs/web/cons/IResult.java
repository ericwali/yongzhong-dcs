//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.cons;

public interface IResult<T> {
    boolean isSuccess();

    String getMessage();

    T getData();
}
