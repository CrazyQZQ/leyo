package com.qq.account.service;

import com.qq.common.system.pojo.SysAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author QinQiang
* @description 针对表【sys_account(账户表)】的数据库操作Service
* @createDate 2022-04-29 14:43:18
*/
public interface SysAccountService extends IService<SysAccount> {

    void operateAccountAmount(Long id, BigDecimal amount);
}
