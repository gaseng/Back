package com.gaseng.global.security.handler;

import com.gaseng.global.exception.BaseException;
import com.gaseng.global.exception.GlobalErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        throw BaseException.type(GlobalErrorCode.INVALID_PERMISSION);
    }
}