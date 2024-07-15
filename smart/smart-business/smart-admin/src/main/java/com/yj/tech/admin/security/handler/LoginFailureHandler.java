package com.yj.tech.admin.security.handler;

import com.yj.tech.common.web.restful.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;

/**
 *  认证失败处理 - 前后端分离情况下返回json数据格式
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String  result;
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            result = e.getMessage();
        } else if (e instanceof LockedException) {
            result = "账户被锁定，请联系管理员!";
        } else if (e instanceof CredentialsExpiredException) {
            result = "证书过期，请联系管理员!";
        } else if (e instanceof AccountExpiredException) {
            result = "账户过期，请联系管理员!";
        } else if (e instanceof DisabledException) {
            result = "账户被禁用，请联系管理员!";
        } else {
            log.error("登录失败：", e);
            result = "登录失败!";
        }

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(Result.errorJSON(result));
    }

}
