package com.qq.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.account.service.SysAccountService;
import com.qq.common.system.mapper.SysAccountMapper;
import com.qq.common.system.pojo.SysAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
* @author QinQiang
* @description 针对表【sys_account(账户表)】的数据库操作Service实现
* @createDate 2022-04-29 14:43:18
*/
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount>
    implements SysAccountService {

    @Override
    public void operateAccountAmount(Long id, BigDecimal amount) {
        SysAccount account = this.getById(id);
        account.setAmount(account.getAmount().add(amount));
        this.updateById(account);
    }
}




