package com.qq.oauth2.demo.exception.enums;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
public enum  ResultCode {

    CLIENT_AUTH_FAILED(1001,"客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003,"不支持的认证类型"),
    NO_PERMISSION(1004,"无权限访问"),
    UNAUTHORIZED(401,"系统错误")
    ;

    private int code;
    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
