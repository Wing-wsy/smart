package com.yj.tech.admin.modules.test;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yj.tech.util.LogUtil;
import com.yj.tech.web.restful.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @author wing
 * @create 2024/6/18
 */
@Tag(name = "测试控制器")
@ApiSupport(order = 2,author = "wing1") // order：当前控制器排序，author：当前控制器作者
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperationSupport(order = 1,author = "wing")  // order：当前接口排序，author：当前接口作者（如果没写，默认使用控制器的作者）
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

    @ApiOperationSupport(order = 2)   //没有当前接口作者，默认使用控制器的作者（如果有写的话）
    @Parameter(name = "name",description = "姓名",required = false)
    @Operation(summary = "示例接口【开发中】")
    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@RequestParam(value = "name",defaultValue = "小王")String name){
        return ResponseEntity.ok("Hi:"+name);
    }

    /**
     * 测试方法
     *
     * @param who 测试参数
     * @return {@link Dict}
     */
    @ApiOperationSupport(order = 3)
    @Operation(summary = "测试aop日志打印")
    @GetMapping("/test")
    public Dict test(String who) {
        return Dict.create().set("who", StrUtil.isBlank(who) ? "me" : who);
    }

    /**
     *  测试post json方法
     * @param map 请求的json参数
     * @return {@link Dict}
     */
    @ApiOperationSupport(order = 4)
    @Operation(summary = "测试aop日志打印-json")
    @PostMapping("/testJson")
    public Dict testJson(@RequestBody Map<String, Object> map) {
        return Dict.create().set("json", map);
    }

}
