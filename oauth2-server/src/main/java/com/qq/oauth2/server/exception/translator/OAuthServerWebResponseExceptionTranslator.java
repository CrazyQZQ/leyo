package com.qq.oauth2.server.exception.translator;

import com.qq.common.core.enums.AuthResultCode;
import com.qq.common.core.web.domain.AjaxResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
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
    public ResponseEntity<AjaxResult> translate(Exception e) {
        AuthResultCode resultCode = doTranslate(e);
        return new ResponseEntity<>(AjaxResult.error(resultCode.getCode(), resultCode.getMsg()), HttpStatus.UNAUTHORIZED);
    }

    private AuthResultCode doTranslate(Exception e) {
        AuthResultCode resultCode = AuthResultCode.UNAUTHORIZED;
        if (e instanceof UnsupportedGrantTypeException) {
            resultCode = AuthResultCode.UNSUPPORTED_GRANT_TYPE;
        } else if (e instanceof InvalidGrantException) {
            resultCode = AuthResultCode.USERNAME_OR_PASSWORD_ERROR;
        } else if (e instanceof InvalidClientException) {
            resultCode = AuthResultCode.UNSUPPORTED_GRANT_TYPE;
        }
        return resultCode;
    }
}
