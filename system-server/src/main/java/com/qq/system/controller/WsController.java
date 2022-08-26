package com.qq.system.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.WsSendVO;
import com.qq.system.service.WsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/8/25
 **/
@RestController
@RequestMapping("system/websocket")
@Slf4j
@Api(tags = "用户管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsController {
    private final WsService wsService;

    /**
     * 给单个用户发消息
     * @param userId
     * @param message
     */
    @Log(title = "system_websocket", funcDesc = "给单个用户发消息")
    @ApiOperation("给单个用户发消息")
    @GetMapping("send/one")
    public AjaxResult sendOneMessage(Long userId, String message) {
        wsService.sendOneMessage(userId, message);
        return AjaxResult.success();
    }

    /**
     * 给多个用户发消息
     * @param sendVO
     */
    @Log(title = "system_websocket", funcDesc = "给多个用户发消息")
    @ApiOperation("给多个用户发消息")
    @GetMapping("send/more")
    public AjaxResult sendMoreMessage(@RequestBody WsSendVO sendVO) {
        wsService.sendMoreMessage(sendVO.getUserIds(), sendVO.getMessage());
        return AjaxResult.success();
    }
}
