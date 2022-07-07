package com.qq.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysAccount;

import java.math.BigDecimal;

/**
 * @author QinQiang
 * @description 针对表【sys_account(账户表)】的数据库操作Service
 * @createDate 2022-04-29 14:43:18
 */
public interface SysAccountService extends IService<SysAccount> {

    /**
     * 账户金额操作
     * @param id
     * @param amount
     */
    void operateAccountAmount(Long id, BigDecimal amount);
}
