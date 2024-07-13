package com.yj.tech.admin.aspectj;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.yj.tech.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 使用 aop 切面记录请求日志信息
 * </p>
 *
 * @author yangkai.shen
 * @author chen qi
 * @date Created in 2018-10-01 22:05
 */
@Aspect
@Component
public class AopLog {
    /**
     * 切入点【modules包下，包括子包以 Controller 结尾的控制器全部方法打印请求接口入参和接口响应出参】
     */
    @Pointcut("execution(public * com.yj.tech.admin.modules..*Controller.*(..))")
    public void log() {

    }

    /**
     * 环绕操作【入参和出参分开打印】
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 请求URI
        String requestURI = request.getRequestURI().toString();
        // 方法名
        String classMethod = String.format("%s.%s", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
      final Log l_request = Log.builder()
        .classMethod(classMethod)
        .requestParams(getNameAndValue(point))
        .timeCost(System.currentTimeMillis() - startTime).build();
        LogUtil.info(getClass(),"Request Log Info : 【{}】 {}", requestURI, JSONUtil.toJsonStr(l_request));


      Object result = point.proceed();
      String header = request.getHeader("User-Agent");
        final Log l_result = Log.builder()
            .classMethod(classMethod)
            .result(result)
            .timeCost(System.currentTimeMillis() - startTime)
            .userAgent(header).build();
        LogUtil.info(getClass(),"Response Log Info : 【{}】 {}", requestURI, JSONUtil.toJsonStr(l_result));


      return result;
    }

    /**
     *  获取方法参数名和参数值
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {

        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            LogUtil.warn(getClass(),"{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                LogUtil.error(AopLog.class,e.getMessage());
            }
        }
        return ip;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Log {
        // 线程id
        private String threadId;
        // 线程名称
        private String threadName;
        // ip
        private String ip;
        // url
        private String url;
        // uri
        private String uri;
        // http方法 GET POST PUT DELETE PATCH
        private String httpMethod;
        // 类方法
        private String classMethod;
        // 请求参数
        private Object requestParams;
        // 返回参数
        private Object result;
        // 接口耗时
        private Long timeCost;
        // 操作系统
        private String os;
        // 浏览器
        private String browser;
        // user-agent
        private String userAgent;
    }
}
