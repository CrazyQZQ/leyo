package com.qq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysMessage;
import com.qq.system.service.SysMessageService;
import com.qq.system.service.WsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/8/25
 **/
@RestController
@RequestMapping("system/message")
@Slf4j
@Api(tags = "用户管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysMessageController extends BaseController<SysMessage> {
    private final WsService wsService;
    private final SysMessageService messageService;

    /**
     * 查询用户消息列表
     *
     * @param userId
     * @return
     */
    @ApiOperation("查询用户消息列表")
    @GetMapping("list")
    @Log(title = "system_message", funcDesc = "查询用户消息列表")
    public AjaxResult getUserMessageList(@RequestParam Long userId) {
        startPage();
        List<SysMessage> list = messageService.list(new LambdaQueryWrapper<SysMessage>().eq(SysMessage::getUserId, userId));
        TableDataInfo dataTable = getDataTable(list, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 已读消息
     *
     * @param id
     * @return
     */
    @ApiOperation("已读消息")
    @GetMapping("readMessage")
    @Log(title = "system_message", funcDesc = "已读消息")
    public AjaxResult readMessage(@RequestParam Long id) {
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<SysMessage>().set(SysMessage::getReadStatus, "1").eq(SysMessage::getId, id);
        boolean update = messageService.update(updateWrapper);
        return update ? AjaxResult.success() : AjaxResult.error("更新消息已读状态失败");
    }

    /**
     * 删除消息
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除消息")
    @PostMapping("deleteMessage")
    @Log(title = "system_message", funcDesc = "删除消息")
    public AjaxResult deleteMessage(@RequestBody List<Long> ids) {
        boolean b = messageService.removeByIds(ids);
        return b ? AjaxResult.success() : AjaxResult.error("删除消息失败");
    }

    /**
     * 给单个用户发消息
     *
     * @param message
     */
    @Log(title = "system_message", funcDesc = "给单个用户发消息")
    @ApiOperation("给单个用户发消息")
    @PostMapping("send/one")
    public AjaxResult sendOneMessage(@RequestBody SysMessage message) {
        wsService.sendOneMessage(message);
        return AjaxResult.success();
    }

    /**
     * 给多个用户发消息
     *
     * @param messages
     */
    @Log(title = "system_message", funcDesc = "给多个用户发消息")
    @ApiOperation("给多个用户发消息")
    @PostMapping("send/more")
    public AjaxResult sendMoreMessage(@RequestBody List<SysMessage> messages) {
        wsService.sendMoreMessage(messages);
        return AjaxResult.success();
    }
}
