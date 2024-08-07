package com.yj.tech.admin.controller.job;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
            LogUtil.error(getClass(), "token没有传值");
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
    @Operation(summary = "分页任务列表")
    @GetMapping("/list")
    public String xxlJobList(HttpServletRequest request, Integer jobGroup, Integer triggerStatus, Integer page, Integer size) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
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

    /**
     * 保存任务
     */
    @Parameters({
            @Parameter(name = "jobGroup",description = "第几组",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "scheduleConf",description = "Cron表达式",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "jobDesc",description = "任务描述",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "author",description = "负责人",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "executorHandler",description = "JobHandler",required = true,in = ParameterIn.QUERY),
    })
    @Operation(summary = "保存任务")
    @PostMapping("/add")
    public String xxlJobAdd(HttpServletRequest request, Integer jobGroup, String scheduleConf,
                            String jobDesc, String author, String executorHandler) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
            return null;
        }
        if (jobGroup == null || scheduleConf == null ||
                jobDesc == null || author == null || executorHandler == null) {
            LogUtil.error(getClass(), "必传参数不能为空");
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("jobGroup", jobGroup);
        jobInfo.put("scheduleType", "CRON");
        jobInfo.put("scheduleConf", scheduleConf);
        jobInfo.put("cronGen_display", scheduleConf);
        jobInfo.put("schedule_conf_CRON", scheduleConf);
        jobInfo.put("jobDesc", jobDesc);
        jobInfo.put("author", author);
        jobInfo.put("executorHandler", executorHandler);
        // 不设置任务参数
        jobInfo.put("executorParam", "");
        // 默认执行方式
        jobInfo.put("executorRouteStrategy", "FIRST");
        jobInfo.put("misfireStrategy", "DO_NOTHING");
        jobInfo.put("executorBlockStrategy", ExecutorBlockStrategyEnum.SERIAL_EXECUTION);
        // 路由策略，默认第一个
        jobInfo.put("glueType", GlueTypeEnum.BEAN);

        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/add").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 修改任务
     */
    @Parameters({
            @Parameter(name = "id",description = "修改任务ID",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "jobGroup",description = "第几组",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "scheduleConf",description = "Cron表达式",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "jobDesc",description = "任务描述",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "author",description = "负责人",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "executorHandler",description = "JobHandler",required = true,in = ParameterIn.QUERY),
    })
    @Operation(summary = "修改任务")
    @PostMapping("/update")
    public String xxlJobUpdate(HttpServletRequest request, Integer jobGroup,
                            Integer id, String scheduleConf,
                            String jobDesc, String author, String executorHandler) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
            return null;
        }
        if (id == null || jobGroup == null || scheduleConf == null ||
                jobDesc == null || author == null || executorHandler == null) {
            LogUtil.error(getClass(), "必传参数不能为空");
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("id", id);
        jobInfo.put("jobGroup", jobGroup);
        jobInfo.put("scheduleType", "CRON");
        jobInfo.put("scheduleConf", scheduleConf);
        jobInfo.put("cronGen_display", scheduleConf);
        jobInfo.put("schedule_conf_CRON", scheduleConf);
        jobInfo.put("jobDesc", jobDesc);
        jobInfo.put("author", author);
        jobInfo.put("executorHandler", executorHandler);
        // 不设置任务参数
        jobInfo.put("executorParam", "");
        // 默认执行方式
        jobInfo.put("executorRouteStrategy", "FIRST");
        jobInfo.put("misfireStrategy", "DO_NOTHING");
        jobInfo.put("executorBlockStrategy", ExecutorBlockStrategyEnum.SERIAL_EXECUTION);

        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/update").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 触发一次任务
     */
    @Parameters({
            @Parameter(name = "id",description = "任务ID值",required = true,in = ParameterIn.QUERY)
    })
    @Operation(summary = "触发一次任务")
    @PostMapping("/trigger")
    public String xxlJobTrigger(HttpServletRequest request, Integer id) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
            return null;
        }
        if (id == null || id <= 0) {
            LogUtil.error(getClass(), "id值无效");
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("id", id);
        jobInfo.put("executorParam", JSONUtil.toJsonStr(jobInfo));

        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/trigger").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }


    /**
     * 删除任务
     */
    @Parameters({
            @Parameter(name = "id",description = "任务ID值",required = true,in = ParameterIn.QUERY)
    })
    @Operation(summary = "删除任务")
    @PostMapping("/remove")
    public String xxlJobRemove(HttpServletRequest request, Integer id) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
            return null;
        }
        if (id == null || id <= 0) {
            LogUtil.error(getClass(), "id值无效");
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("id", id);

        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/remove").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 停止任务
     */
    @Parameters({
            @Parameter(name = "id",description = "任务ID值",required = true,in = ParameterIn.QUERY)
    })
    @Operation(summary = "停止任务")
    @PostMapping("/stop")
    public String xxlJobStop(HttpServletRequest request, Integer id) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
            return null;
        }
        if (id == null || id <= 0) {
            LogUtil.error(getClass(), "id值无效");
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("id", id);

        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/stop").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 启动任务
     */
    @Parameters({
            @Parameter(name = "id",description = "任务ID值",required = true,in = ParameterIn.QUERY)
    })
    @Operation(summary = "启动任务")
    @PostMapping("/start")
    public String xxlJobStart(HttpServletRequest request, Integer id) {
        // 有 token 可以访问（为了安全期间，后续可以判断只能管理员权限才能访问）
        String token = request.getHeader("Authorization");
        if (token == null) {
            LogUtil.error(getClass(), "token没有传值");
            return null;
        }
        if (id == null || id <= 0) {
            LogUtil.error(getClass(), "id值无效");
            return null;
        }
        LogUtil.info(getClass(), "接收到的token:{}", token);
        // 设置请求头参数
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");

        Map<String, Object> jobInfo = new HashMap<>();
        jobInfo.put("id", id);

        HttpResponse execute = HttpUtil.createGet(BASE_URI + JOB_INFO_URI + "/start").addHeaders(headers).form(jobInfo).execute();
        LogUtil.info(getClass(), "【execute】= {}", execute);
        return execute.body();
    }

}
