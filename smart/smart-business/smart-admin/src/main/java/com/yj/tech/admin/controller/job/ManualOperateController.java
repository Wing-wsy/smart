package com.yj.tech.admin.controller.job;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.yj.tech.admin.config.XxlJobConfig;
import com.yj.tech.common.entity.User;
import com.yj.tech.common.util.LogUtil;
import com.yj.tech.common.web.restful.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 手动操作 xxl-job
 */
@Tag(name = "任务控制器")
@ApiSupport(order = 3,author = "wing") // order：当前控制器排序，author：当前控制器作者
@RestController
@RequestMapping("/xxl-job")
public class ManualOperateController {

    @Value("${xxl.job.admin.addresses}")
    private String BASE_URI;

    @Value("${xxl.job.admin.jobInfoUri}")
    private String JOB_INFO_URI;

    @Value("${xxl.job.admin.jobGroupUri}")
    private String JOB_GROUP_URI;


    /**
     * 任务组列表，xxl-job叫做触发器列表
     */
    @ApiOperationSupport(order = 1, author = "wing")
    @Operation(summary = "任务组列表")
    @GetMapping("/group")
    public String xxlJobGroup(HttpServletRequest request) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        // 创建HTTP请求
        HttpRequest req = HttpRequest.post(BASE_URI + JOB_GROUP_URI + "/pageList").addHeaders(headers);
        // 发送请求
        HttpResponse response = req.execute();
        LogUtil.info(getClass(), "【execute】= {}", response);
        return response.body();
    }

    /**
     * 分页任务列表
     *
     * @param page 当前页，第一页 -> 0
     * @param size 每页条数，默认10
     * @return 分页任务列表
     */
    @Parameters({
            @Parameter(name = "jobGroup",description = "第几组（默认查询第1组）",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "triggerStatus",description = "状态（-1全部，0停止，1启动；默认查询全部）",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "page",description = "第几页（默认第1页）",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "size",description = "每页条数（默认每页10条）",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "用户分页查询")
    @GetMapping("/list")
    public String xxlJobList(HttpServletRequest request, Integer jobGroup, Integer triggerStatus, Integer page, Integer size) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");
        // 设置请求参数
        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("start", page != null ? page : 0);
        jobInfo.put("length", size != null ? size : 10);
        jobInfo.put("jobGroup", jobGroup != null ? jobGroup : 1);
        jobInfo.put("triggerStatus", triggerStatus != null ? triggerStatus : -1);
        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/pageList").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }

//    /**
//     * 测试手动保存任务
//     */
//    @GetMapping("/add")
//    public String xxlJobAdd() {
//        Map<String, Object> jobInfo = new HashMap<>();
//        jobInfo.put("jobGroup", 2);
//        jobInfo.put("jobCron", "0 0/1 * * * ? *");
//        jobInfo.put("jobDesc", "手动添加的任务");
//        jobInfo.put("author", "admin");
//        jobInfo.put("executorRouteStrategy", "ROUND");
//        jobInfo.put("executorHandler", "demoTask");
//        jobInfo.put("executorParam", "手动添加的任务的参数");
//        jobInfo.put("executorBlockStrategy", ExecutorBlockStrategyEnum.SERIAL_EXECUTION);
//        jobInfo.put("glueType", GlueTypeEnum.BEAN);
//
//        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/add").form(jobInfo).execute();
//        LogUtil.info(getClass(), "【execute】= {}", execute);
//        return execute.body();
//    }
//
//    /**
//     * 测试手动触发一次任务
//     */
//    @GetMapping("/trigger")
//    public String xxlJobTrigger() {
//        Map<String, Object> jobInfo = new HashMap<>();
//        jobInfo.put("id", 4);
//        jobInfo.put("executorParam", JSONUtil.toJsonStr(jobInfo));
//
//        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/trigger").form(jobInfo).execute();
//        LogUtil.info(getClass(), "【execute】= {}", execute);
//        return execute.body();
//    }
//
//    /**
//     * 测试手动删除任务
//     */
//    @GetMapping("/remove")
//    public String xxlJobRemove() {
//        Map<String, Object> jobInfo = new HashMap<>();
//        jobInfo.put("id", 4);
//
//        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/remove").form(jobInfo).execute();
//        LogUtil.info(getClass(), "【execute】= {}", execute);
//        return execute.body();
//    }
//
//    /**
//     * 测试手动停止任务
//     */
//    @GetMapping("/stop")
//    public String xxlJobStop() {
//        Map<String, Object> jobInfo = new HashMap<>();
//        jobInfo.put("id", 4);
//
//        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/stop").form(jobInfo).execute();
//        LogUtil.info(getClass(), "【execute】= {}", execute);
//        return execute.body();
//    }
//
//    /**
//     * 测试手动启动任务
//     */
//    @GetMapping("/start")
//    public String xxlJobStart() {
//        Map<String, Object> jobInfo = new HashMap<>();
//        jobInfo.put("id", 4);
//
//        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/start").form(jobInfo).execute();
//        LogUtil.info(getClass(), "【execute】= {}", execute);
//        return execute.body();
//    }

}
