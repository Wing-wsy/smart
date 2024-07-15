package com.yj.tech.admin.controller.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yj.tech.common.util.LogUtil;
import com.yj.tech.common.web.restful.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wing
 * @create 2024/6/18
 */
@Tag(name = "测试控制器")
@ApiSupport(order = 2,author = "wing1") // order：当前控制器排序，author：当前控制器作者
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperationSupport(order = 1, author = "wing")
    @Operation(summary = "打印日志")
    @GetMapping("/log")
    public Result isFollow() {

        LogUtil.trace(getClass(), "这是trace日志");
        LogUtil.debug(getClass(), "这是debug日志");
        LogUtil.info(getClass(), "这是info日志");
        LogUtil.warn(getClass(), "这是warn日志");
        LogUtil.error(getClass(), "这是error日志");
        return Result.success();
    }

}
