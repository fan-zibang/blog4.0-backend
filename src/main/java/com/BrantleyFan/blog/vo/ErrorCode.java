package com.BrantleyFan.blog.vo;

public enum ErrorCode {

    PARAMS_ERROR(1001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(1002,"用户名或密码不存在"),
    TOKEN_ERROR(1003,"TOKEN不合法"),
    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"用户未登录");


    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
