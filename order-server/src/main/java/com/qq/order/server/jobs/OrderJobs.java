package com.qq.order.server.jobs;

import cn.hutool.core.bean.BeanUtil;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.SysSku;
import com.qq.order.server.mapper.SysOrderDetailMapper;
import com.qq.order.server.service.SkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 定时任务
 * @Author QinQiang
 * @Date 2022/6/8
 **/
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OrderJobs {

    private final RedisService redisService;

    private final SysOrderDetailMapper sysOrderDetailMapper;

    private final SkuService skuService;

    private final RestTemplate restTemplate;

    @Scheduled(cron = "0/20 * * * * ?")
    public void updateHotSale() {
        log.info("更新热销商品定时任务开始");
        List<Long> hostSalesSkuIds = sysOrderDetailMapper.selectHostSales();
        List<SysSku> hostSales = new ArrayList<>(hostSalesSkuIds.size());
        for (Long skuId : hostSalesSkuIds) {
            ResponseEntity<AjaxResult> entity = restTemplate.getForEntity("http://product-server/product/sku/getById?id=" + skuId, AjaxResult.class);
            AjaxResult ajaxResult = entity.getBody();
            if (ajaxResult.isSuccess()) {
                SysSku sku = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysSku.class, true, null);
                hostSales.add(sku);
            } else {
                log.error("更新热销商品(skuId:{})定时任务异常：{}", skuId, ajaxResult.getMessage());
            }
        }
        redisService.deleteObject(CacheConstants.HOT_SALE_KEY);
        redisService.setCacheList(CacheConstants.HOT_SALE_KEY, hostSales);
    }
}
