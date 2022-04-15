package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/15
 **/
@Data
public class JwtInfo {

    /**
     * token唯一id
     */
    @TableField(exist = false)
    private String jti;

    /**
     * token过期时间
     */
    @TableField(exist = false)
    private Long expireIn;
}
