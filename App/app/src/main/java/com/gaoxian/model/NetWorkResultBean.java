package com.gaoxian.model;

import com.google.gson.annotations.Expose;

/**
 *
 * <p/>
 * 设计规范：
 * 1、子类使用static 关键字，每个类的属性，只能由该类的成员方法来获取，父类或者其他平行类没有权限获取该类的所有成员属性。也即谁的孩子，由谁来领养！
 * 3、按照retrofit 和 Gson 说明文档，必须为 每个json的字段名都指定 注解@Expose，要注意的是，若字段格式为：rsm:{a:"123",b:"456"}则rsm 就是一个子类，如下面的代码所示；若字段格式为：comments:[{...},{...},...],则comments是List<类名>，类名是型如rsm字段的类名
 */

public class NetWorkResultBean<T> {

    @Expose
    private int MessageCode;
    @Expose
    private String Message;
    @Expose
    private T data;

    @Override
    public String toString() {
        return super.toString();
    }

    public int getMessageCode() {
        return MessageCode;
    }

    public void setMessageCode(int messageCode) {
        MessageCode = messageCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}