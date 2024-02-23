package com.java.hotpotserver.vo;

public class Result<T> {//传给前端的接口调用结果，包括code表示成功状态、msg表示信息、data表示数据
    public int code;
    public String msg;
    public T data;
    public static <T> Result success() {

        Result s = new Result(200,"操作成功",null);
        return s;

    }
    public Result(int code,String msg,T data)
    {
        this.msg=msg;
        this.data=data;
        this.code=code;

    }
    public static <T> Result success(T data) {
        Result s  = new Result(200,"操作成功",data);
        return s;
    }
    public static <T> Result fail() {
        Result s  = new Result(404,"操作失败",null);
        return s;
    }


}
