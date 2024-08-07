package com.yj.tech.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yj.tech.common.web.restful.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class NoAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)  {
        LogUtil.info(getClass(), "认证未通过:{}",accessDeniedException.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(500);
        response.getWriter().write(Result.errorJSON(accessDeniedException.getMessage()));
    }
}
