package com.qq.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.account.service.SysAccountService;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @Description: 账户管理
 * @Author QinQiang
 * @Date 2022/4/28
 **/

@RequestMapping("account")
@RestController
@Api(tags = "账户管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final SysAccountService accountService;

    /**
     * 账户id获取账户信息
     *
     * @param id
     * @return
     */
    @ApiOperation("账户id获取账户信息")
    @GetMapping("/getAccountById")
    @Log(title = "account", funcDesc = "账户id获取账户信息")
    public AjaxResult getAccountById(@ApiParam("账户id") @RequestParam Long id) {
        return AjaxResult.success(accountService.getById(id));
    }

    /**
     * 用户id获取账户信息
     *
     * @param userId
     * @return
     */
    @ApiOperation("用户id获取账户信息")
    @GetMapping("/getAccountByUserId")
    @Log(title = "account", funcDesc = "用户id获取账户信息")
    public AjaxResult getAccountByUserId(@ApiParam("用户id") @RequestParam Long userId) {
        return AjaxResult.success(accountService.getOne(new QueryWrapper<SysAccount>().eq("user_id", userId)));
    }

    /**
     * 添加账户
     *
     * @param account
     * @param bindingResult
     * @return
     */
    @ApiOperation("添加账户")
    @PostMapping("/addAccount")
    @Log(title = "account", funcDesc = "添加账户")
    public AjaxResult add(@Valid @RequestBody SysAccount account, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return AjaxResult.error(errorMsg);
        }
        int count = accountService.count(new QueryWrapper<SysAccount>().eq("account_name", account.getAccountName()));
        if (count > 0) {
            return AjaxResult.error("账户已存在");
        }
        return AjaxResult.success(accountService.save(account));
    }

    /**
     * 更新账户
     *
     * @param account
     * @param bindingResult
     * @return
     */
    @ApiOperation("更新账户")
    @PutMapping("/updateAccount")
    @Log(title = "account", funcDesc = "更新账户")
    public AjaxResult update(@Valid @RequestBody SysAccount account, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return AjaxResult.error(errorMsg);
        }
        int count = accountService.count(new QueryWrapper<SysAccount>().eq("account_name", account.getAccountId()));
        if (count < 1) {
            return AjaxResult.error("账户不存在");
        }
        return AjaxResult.success(accountService.updateById(account));
    }

    /**
     * 删除账户
     *
     * @param id
     * @return
     */
    @ApiOperation("删除账户")
    @DeleteMapping("/deleteAccount")
    @Log(title = "account", funcDesc = "删除账户")
    public AjaxResult delete(@ApiParam("账户id") Long id) {
        return AjaxResult.success(accountService.removeById(id));
    }

    /**
     * 账户金额操作
     *
     * @param id
     * @param amount
     * @return
     */
    @ApiOperation("账户金额操作")
    @PutMapping("/operateAccountAmount")
    @Log(title = "account", funcDesc = "账户金额操作")
    public AjaxResult operateAccountAmount(@ApiParam("账户id") @RequestParam Long id, @ApiParam("操作金额") @RequestParam BigDecimal amount) {
        accountService.operateAccountAmount(id, amount);
        return AjaxResult.success();
    }
}