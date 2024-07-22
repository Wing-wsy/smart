package com.yj.tech.admin.controller.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yj.tech.common.entity.Menu;
import com.yj.tech.common.util.JsonUtil;
import com.yj.tech.common.util.LogUtil;
import com.yj.tech.common.web.restful.Result;
import com.yj.tech.redis.service.RedisService;
import com.yj.tech.redis.util.RedisKeyUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wing
 * @create 2024/6/18
 */
@Tag(name = "测试控制器")
@ApiSupport(order = 2,author = "wing1") // order：当前控制器排序，author：当前控制器作者
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RedisService redisService;

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

    @ApiOperationSupport(order = 1, author = "wing")
    @Operation(summary = "redis测试")
    @GetMapping("/redis")
    public Result isRedis() {

        String key = "name";
        String value = "abc";
        //写入一条String数据
        redisTemplate.opsForValue().set(key, value);
        //获取String数据
        Object valueObj = redisTemplate.opsForValue().get(key);
        System.out.println(valueObj);

        // 获取 key
        String key1 = RedisKeyUtil.getKey("1","2","3");
        System.out.println(key1);

        // 设置 name key 有效期 20秒
        redisService.expireKey("name",20, TimeUnit.SECONDS);

        return Result.success();
    }

    @GetMapping("/testJson")
    public Result testJson() {
        System.out.println("11");
        Menu menuParent = new Menu();
        menuParent.setId(0L);
        menuParent.setName("顶层");

        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("菜单");
        menu.setParent(menuParent);

        // 对象转成
        String jsonString = JsonUtil.getJsonString(menu);
        System.out.println(jsonString);
        return Result.success();
    }


}
