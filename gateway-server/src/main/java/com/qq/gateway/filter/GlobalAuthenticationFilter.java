package com.qq.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.constant.TokenConstants;
import com.qq.common.core.enums.AuthResultCode;
import com.qq.common.core.utils.DateUtils;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.core.utils.sign.Base64;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.redis.service.RedisService;
import com.qq.gateway.config.SysParameterConfig;
import com.qq.gateway.pojo.RequestLogInfo;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 公众号：码猿技术专栏
 * 全局过滤器，对token的拦截，解析token放入header中，便于下游微服务获取用户信息
 * 分为如下几步：
 *  1、白名单直接放行
 *  2、校验token
 *  3、读取token中存放的用户信息
 *  4、重新封装用户信息，加密成功json数据放入请求头中传递给下游微服务
 */
@Component
@Slf4j
public class GlobalAuthenticationFilter implements GlobalFilter, Ordered {
    /**
     * JWT令牌的服务
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private RedisService redisService;

    /**
     * 系统参数配置
     */
    @Autowired
    private SysParameterConfig sysConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("GlobalAuthenticationFilter filter start: {}", exchange.getRequest().getURI());
        String requestUrl = exchange.getRequest().getPath().value();
        //1、白名单放行，比如授权服务、静态资源.....
        if (StringUtils.matches(requestUrl,sysConfig.getIgnoreUrls())){
            return chain.filter(exchange);
        }

        //2、 检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)) {
            return invalidTokenMono(exchange);
        }

        //3 判断是否是有效的token
        OAuth2AccessToken oAuth2AccessToken;
        try {
            //解析token，使用tokenStore
            oAuth2AccessToken = tokenStore.readAccessToken(token);
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            //令牌的唯一ID
            String jti=additionalInformation.get(TokenConstants.JTI).toString();
            /**查看黑名单中是否存在这个jti，如果存在则这个令牌不能用****/
            Boolean hasKey = redisService.hasKey(AuthConstants.JTI_KEY_PREFIX + jti);
            if (hasKey)
                return invalidTokenMono(exchange);
            //取出用户身份信息
            String user_name = additionalInformation.get("user_name").toString();
            //获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");
            //从additionalInformation取出userId
            String userId = additionalInformation.get(TokenConstants.USER_ID).toString();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(TokenConstants.PRINCIPAL_NAME, user_name);
            jsonObject.put(TokenConstants.AUTHORITIES_NAME,authorities);
            //过期时间，单位秒
            jsonObject.put(TokenConstants.EXPR,oAuth2AccessToken.getExpiresIn());
            jsonObject.put(TokenConstants.JTI,jti);
            //封装到JSON数据中
            jsonObject.put(TokenConstants.USER_ID, userId);
            //将解析后的token加密放入请求头中，方便下游微服务解析获取用户信息
            String base64 = Base64.encode(jsonObject.toJSONString().getBytes());
            //放入请求头中
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header(TokenConstants.TOKEN_NAME, base64).build();

            ServerHttpResponseDecorator serverHttpResponseDecorator = printLog(exchange);
            ServerWebExchange build = exchange.mutate().request(tokenRequest).response(serverHttpResponseDecorator).build();
            return chain.filter(build);
        } catch (InvalidTokenException e) {
            //解析token异常，直接返回token无效
            return invalidTokenMono(exchange);
        }


    }

    @Override
    public int getOrder() {
        return -2;
    }

    // /**
    //  * 对url进行校验匹配
    //  */
    // private boolean checkUrls(List<String> urls,String path){
    //     AntPathMatcher pathMatcher = new AntPathMatcher();
    //     for (String url : urls) {
    //         if (pathMatcher.match(url,path))
    //             return true;
    //     }
    //     return false;
    // }

    /**
     * 从请求头中获取Token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    /**
     * 无效的token
     */
    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {

        return buildReturnMono(new AjaxResult(AuthResultCode.INVALID_TOKEN.getCode(),AuthResultCode.INVALID_TOKEN.getMsg()), exchange);
    }


    private Mono<Void> buildReturnMono(AjaxResult resultMsg, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        byte[] bits = JSON.toJSONString(resultMsg).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset:utf-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 打印日志
     * @param exchange
     * @return
     */
    private ServerHttpResponseDecorator printLog(ServerWebExchange exchange){
        long start = System.currentTimeMillis();

        RequestLogInfo logInfo = new RequestLogInfo();
        // 获取请求信息
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress address = request.getRemoteAddress();
        String method = request.getMethodValue();
        URI uri = request.getURI();

        logInfo.setRequestId(request.getId());
        logInfo.setRequestUrl(uri.getPath());
        logInfo.setRequestTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
        // 获取请求body
        if("POST".equals(method)){
            //获取请求体
            Flux<DataBuffer> body = request.getBody();

            AtomicReference<String> bodyRef = new AtomicReference<>();
            body.subscribe(buffer -> {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
                DataBufferUtils.release(buffer);
                bodyRef.set(charBuffer.toString());
            });
            //获取request body
            logInfo.setRequestBody(bodyRef.get());
        }
        // 获取请求query
        logInfo.setRequestParam(JSON.toJSONString(request.getQueryParams()));
        logInfo.setRemoteAddr(address.getHostName()+address.getPort());
        logInfo.setRequestMethod(method);

        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        String responseResult = new String(content, Charset.forName("UTF-8"));
                        logInfo.setResponseCode(this.getStatusCode().toString());
                        logInfo.setResponseBody(responseResult);

                        // 计算请求时间
                        long end = System.currentTimeMillis();
                        logInfo.setResponseTime(end - start);
                        log.info(JSON.toJSONString(logInfo));
                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return decoratedResponse;
    }
}
