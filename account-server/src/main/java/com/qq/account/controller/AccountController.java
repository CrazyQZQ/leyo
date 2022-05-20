package com.qq.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.account.service.SysAccountService;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/28
 **/

@RequestMapping("account")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final SysAccountService accountService;

    @GetMapping("/getAccountById")
    @Log(title = "account",funcDesc = "获取账户信息")
    public AjaxResult getAccountById(Long id){
        if(id == null){
            return AjaxResult.error("账户id不能为空");
        }
        return AjaxResult.success(accountService.getById(id));
    }

    @PostMapping("/addAccount")
    @Log(title = "account",funcDesc = "添加账户")
    public AjaxResult add(@Valid @RequestBody SysAccount account, BindingResult bindingResult){
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return AjaxResult.error(errorMsg);
        }
        int count = accountService.count(new QueryWrapper<SysAccount>().eq("account_name", account.getAccountId()));
        if(count > 0){
            return AjaxResult.error("账户已存在");
        }
        return AjaxResult.success(accountService.save(account));
    }

    @PutMapping("/updateAccount")
    @Log(title = "account",funcDesc = "更新账户")
    public AjaxResult update(@Valid @RequestBody SysAccount account, BindingResult bindingResult){
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return AjaxResult.error(errorMsg);
        }
        int count = accountService.count(new QueryWrapper<SysAccount>().eq("account_name", account.getAccountId()));
        if(count < 1){
            return AjaxResult.error("账户不存在");
        }
        return AjaxResult.success(accountService.updateById(account));
    }

    @DeleteMapping("/deleteAccount")
    @Log(title = "account",funcDesc = "删除账户")
    public AjaxResult delete(Long id){
        return AjaxResult.success(accountService.removeById(id));
    }

    @PostMapping("/operateAccountAmount")
    @Log(title = "account",funcDesc = "账户金额操作")
    public AjaxResult operateAccountAmount(Long id, BigDecimal amount){
        accountService.operateAccountAmount(id, amount);
        return AjaxResult.success();
    }
}