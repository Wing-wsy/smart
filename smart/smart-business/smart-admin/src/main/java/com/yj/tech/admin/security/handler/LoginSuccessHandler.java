package com.yj.tech.admin.security.handler;

import com.yj.tech.admin.util.JwtUtils;
import com.yj.tech.common.entity.User;
import com.yj.tech.common.entity.Role;
import com.yj.tech.common.util.date.DateUtils;
import com.yj.tech.common.web.restful.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  登录成功处理器
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException {

        User user = (User) auth.getPrincipal();

        //生成token
        String token = JwtUtils.token(auth);
        response.addHeader("token", token);

        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> data = new HashMap<>();
        data.put("username",user.getUsername());
        data.put("roles",user.getRoles().stream().map(Role::getTag).collect(Collectors.toList()));
        data.put("accessToken",token);
        data.put("refreshToken",token);
        data.put("expires", DateUtils.format(DateUtils.addDay(30)));
        LogUtil.info(getClass(), "登录成功 {}",user.getUsername());


        response.getWriter().write(Result.okJSON(data));
    }

}
