package com.qq.common.core.enums;

/**
 * @author 公众号：码猿技术专栏
 * 响应码、提示信息
 */
public enum AuthResultCode {

    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),

    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),

    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),

    NO_PERMISSION(1005,"无权限访问！"),
    UNAUTHORIZED(401, "系统错误"),

    INVALID_TOKEN(1004,"无效的token");



    private final int code;

    private final String msg;

    private AuthResultCode(int code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}