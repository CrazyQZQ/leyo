package com.qq.common.core.constant;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
public class AuthConstants {
    /**
     * 权限<->url对应的KEY
     */
    public final static String OAUTH_URLS="oauth2:oauth_urls";

    /**
     * JWT令牌黑名单的KEY
     */
    public final static String JTI_KEY_PREFIX="oauth2:black:";

    /**
     * 角色前缀
     */
    public final static String ROLE_PREFIX="ROLE_";

    public final static String METHOD_SUFFIX=":";

    public final static String ROLE_ROOT_CODE="ROLE_ROOT";

    public final static String LOGIN_VAL_ATTRIBUTE="loginVal_attribute";
}
