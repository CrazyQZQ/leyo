package com.qq.common.core.constant;

/**
 * Token的Key常量
 * 
 * @author ruoyi
 */
public class TokenConstants
{
    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * JWT的秘钥
     * TODO 实际项目中需要统一配置到配置文件中，资源服务也需要用到
     */
    public final static String SIGN_KEY="lxqq";

    public final static String TOKEN_NAME="jwt-token";

    public final static String PRINCIPAL_NAME="principal";

    public static final String AUTHORITIES_NAME="authorities";

    public static final String USER_ID="user_id";

    /**
     * jwt token id
     */
    public static final String JTI="jti";

    public static final String EXPR="expr";

}
