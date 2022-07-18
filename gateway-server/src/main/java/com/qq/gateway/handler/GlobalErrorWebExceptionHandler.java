package com.qq.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qq.common.core.enums.ExceptionCode;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.domain.AjaxResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description: 全局异常处理
 * @Author QinQiang
 * @Date 2022/4/19
 **/
@Order(-1)
@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 设置返回JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                log.debug("gateway异常信息：", ex);
                ServiceException serviceException = doTranslate(ex);
                //返回响应结果
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(AjaxResult.error(serviceException.getCode(), serviceException.getMessage())));
            } catch (JsonProcessingException e) {
                log.error("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    private ServiceException doTranslate(Throwable e) {
        ServiceException serviceException;
        if (e instanceof UnsupportedGrantTypeException) {
            serviceException = new ServiceException(ExceptionCode.UNSUPPORTED_GRANT_TYPE.getMsg(), ExceptionCode.UNSUPPORTED_GRANT_TYPE.getCode());
        } else if (e instanceof InvalidGrantException) {
            serviceException = new ServiceException(ExceptionCode.USERNAME_OR_PASSWORD_ERROR.getMsg(), ExceptionCode.USERNAME_OR_PASSWORD_ERROR.getCode());
        } else if (e instanceof InvalidClientException) {
            serviceException = new ServiceException(ExceptionCode.UNSUPPORTED_GRANT_TYPE.getMsg(), ExceptionCode.UNSUPPORTED_GRANT_TYPE.getCode());
        } else if (e instanceof InvalidTokenException) {
            serviceException = new ServiceException(ExceptionCode.INVALID_TOKEN.getMsg(), ExceptionCode.INVALID_TOKEN.getCode());
        } else {
            serviceException = new ServiceException(e.getMessage(), ExceptionCode.BUSINESS_ERROR.getCode());
        }
        return serviceException;
    }
}
