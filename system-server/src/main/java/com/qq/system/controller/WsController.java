package com.qq.system.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.WsMessageVO;
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
@RequestMapping("system/websocket")
@Slf4j
@Api(tags = "用户管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsController {
    private final WsService wsService;

    /**
     * 给单个用户发消息
     * @param messageVO
     */
    @Log(title = "system_websocket", funcDesc = "给单个用户发消息")
    @ApiOperation("给单个用户发消息")
    @PostMapping("send/one")
    public AjaxResult sendOneMessage(@RequestBody WsMessageVO messageVO) {
        wsService.sendOneMessage(messageVO);
        return AjaxResult.success();
    }

    /**
     * 给多个用户发消息
     * @param messageVOs
     */
    @Log(title = "system_websocket", funcDesc = "给多个用户发消息")
    @ApiOperation("给多个用户发消息")
    @PostMapping("send/more")
    public AjaxResult sendMoreMessage(@RequestBody List<WsMessageVO> messageVOs) {
        wsService.sendMoreMessage(messageVOs);
        return AjaxResult.success();
    }
}
