package com.qq.common.core.enums;

/**
 * @Description: 响应码、提示信息枚举类
 * @Author QinQiang
 * @Date 2022/4/14
 **/
public enum ExceptionCode {

    BUSINESS_ERROR(1000, "业务异常"),

    CLIENT_AUTHENTICATION_FAILED(1001, "客户端认证失败"),

    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),

    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),

    INVALID_TOKEN(1004, "无效的token"),

    NO_PERMISSION(1005, "无权限访问！"),

    UNAUTHORIZED(1006, "用户未授权"),

    ;


    private final int code;

    private final String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
