package com.yj.tech.admin.security.filter;

import cn.hutool.json.JSONUtil;
import com.yj.tech.admin.common.Constant;
import com.yj.tech.admin.util.JwtUtils;
import com.yj.tech.admin.util.Tools;
import com.yj.tech.common.web.restful.Result;
import com.yj.tech.utils.logger.LogUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 登录之前获取token校验:如果有token、再去校验token的合法性:如果没有报错 则认为登录成功
 * 【token是根据SpringSecurity的Authentication生成的,相当于token就是SpringSecurity认证后的凭证】
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  {
        String requestURI = request.getRequestURI();

        //匿名地址直接访问
        if(Tools.contains(requestURI, Constant.annos)){
            filterChain.doFilter(request, response);
            return;
        }

        //获取JWT
        String token = request.getHeader("Authorization");
        LogUtil.info(getClass(),"接收到的token:{}",token);
        if (token != null) {
            try {
                JwtUtils.tokenVerify(token);
                filterChain.doFilter(request, response);
                return;
            }catch (Exception e){
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSONUtil.toJsonStr(Result.error("非法token")));
                return;
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(Result.error("token为空")));
            return;
        }
    }
}
