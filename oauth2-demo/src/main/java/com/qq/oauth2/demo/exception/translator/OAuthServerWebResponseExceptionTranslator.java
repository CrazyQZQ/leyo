package com.qq.oauth2.demo.exception.translator;

import com.qq.oauth2.demo.exception.enums.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<ResultCode> translate(Exception e) throws Exception {
        ResultCode resultCode = doTranslate(e);
        return new ResponseEntity<>(resultCode, HttpStatus.UNAUTHORIZED);
    }

    private ResultCode doTranslate(Exception e){
        ResultCode resultCode = ResultCode.UNAUTHORIZED;
        if(e instanceof UnsupportedGrantTypeException){
            resultCode = ResultCode.UNSUPPORTED_GRANT_TYPE;
        }else if(e instanceof InvalidGrantException){
            resultCode = ResultCode.USERNAME_OR_PASSWORD_ERROR;
        }
        return resultCode;
    }
}
