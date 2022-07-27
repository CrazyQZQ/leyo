package com.qq.common.core.constant;

/**
 * 缓存的key 常量
 *
 * @author ruoyi
 */
public class CacheConstants {
    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 权限缓存前缀
     */
    public final static String MENU_ROLES = "menu_roles:";

    /**
     * 业务日志缓存
     */
    public final static String LOGS_KEY = "business_log";

    /**
     * 错误日志缓存
     */
    public final static String ERROR_LOGS_KEY = "error_logs";

    /**
     * 公告
     */
    public final static String ANNOUNCEMENT_KEY = "announcement";

    /**
     * 重复提交
     */
    public final static String REPEAT_COMMIT_KEY_PREFIX = "repeat_commit:";

    /**
     * 热卖商品
     */
    public final static String HOT_SALE_KEY = "hot_sales";

}
