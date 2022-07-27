package com.qq.es.server.jobs;

import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.json.JsonData;
import com.qq.common.es.constants.IndexConstants;
import com.qq.common.es.service.EsService;
import com.qq.common.es.vo.QueryVo;
import com.qq.common.es.vo.SearchCommonVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 定时删除es日志
 * @Author QinQiang
 * @Date 2022/7/27
 **/
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class LogDeleteJob {
    private final EsService esService;

    /**
     * 每三天删一次三天前的业务日志
     */
    @Scheduled(cron = "0 0 0 1/3 * ? ")
    public void deleteLogs(){
        log.info("删除es业务日志定时任务开始");
        SearchCommonVO searchCommonVO = new SearchCommonVO();
        searchCommonVO.setIndexName(IndexConstants.INDEX_BUSINESS_LOG);
        List<QueryVo> queryVoList = new ArrayList<>();
        QueryVo queryVo = new QueryVo();
        queryVo.setQueryType("1");
        queryVo.setField("@timestamp");
        // 删除3天前的数据
        queryVo.setLte(JsonData.of("now-3d"));
        queryVo.setFormat("epoch_millis");
        queryVoList.add(queryVo);
        searchCommonVO.setQueryVos(queryVoList);
        try {
            DeleteByQueryResponse response = esService.deleteByQuery(searchCommonVO);
            log.info("删除es业务日志定时任务，共发现{}条，已删除{}条", response.total(), response.deleted());
        } catch (IOException e) {
            log.debug("删除es业务日志定时任务失败，：", e);
        }
    }
}
