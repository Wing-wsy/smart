package com.yj.tech.admin.controller.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yj.tech.admin.controller.test.mq.producer.TestMqDirectProducer;
import com.yj.tech.common.entity.Menu;
import com.yj.tech.common.util.JsonUtil;
import com.yj.tech.common.util.LogUtil;
import com.yj.tech.common.web.restful.Result;
import com.yj.tech.redis.lock.RedisDistributedLock;
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

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Resource
    private RedisService redisService;

    @Autowired
    private TestMqDirectProducer testMqDirectProducer;


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

    @ApiOperationSupport(order = 1, author = "wing")
    @Operation(summary = "redis锁测试[非阻塞式获取锁]")
    @GetMapping("/redisRock")
    public Result isRedisRock() {

        String key = "redis:rock:1";
        boolean flag = redisDistributedLock.tryLock(key);
        if (!flag) {
            System.out.println(Thread.currentThread() + ":获取锁失败");
            return Result.error(null);
        }
        try {
            System.out.println(Thread.currentThread() + ":获取锁成功");
            // 处理业务 。。。
            System.out.println(Thread.currentThread() + ":处理业务 。。。");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread() + ":处理业务完成");
        } finally {
            System.out.println(Thread.currentThread() + ":释放锁");
            redisDistributedLock.unlock(key);
        }

        return Result.success();
    }

    @ApiOperationSupport(order = 1, author = "wing")
    @Operation(summary = "redis锁测试[阻塞式获取锁]")
    @GetMapping("/redisRock1")
    public Result isRedisRock1() {

        String key = "redis:rock:1";
        redisDistributedLock.lock(key);
        try {
            System.out.println(Thread.currentThread() + ":获取锁成功");
            // 处理业务 。。。
            System.out.println(Thread.currentThread() + ":处理业务 。。。");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread() + ":处理业务完成");
        } finally {
            System.out.println(Thread.currentThread() + ":释放锁");
            redisDistributedLock.unlock(key);
        }

        return Result.success();
    }

    @ApiOperationSupport(order = 1, author = "wing")
    @Operation(summary = "redis锁测试[非阻塞式获取锁，带有超时时间]")
    @GetMapping("/redisRock2")
    public Result isRedisRock2() {

        String key = "redis:rock:1";
        LogUtil.info(getClass(),"{}:尝试获取锁", Thread.currentThread());
        boolean flag = redisDistributedLock.tryLock(key, 20);
        if (!flag) {
            LogUtil.info(getClass(),"{}:获取锁失败", Thread.currentThread());
            return Result.error(null);
        }
        try {
            LogUtil.info(getClass(),"{}:获取锁成功", Thread.currentThread());
            // 处理业务 。。。
            LogUtil.info(getClass(),"{}:处理业务 。。。", Thread.currentThread());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LogUtil.info(getClass(),"{}:处理业务完成!", Thread.currentThread());
        } finally {
            LogUtil.info(getClass(),"{}:释放锁", Thread.currentThread());
            redisDistributedLock.unlock(key);
        }

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

    @GetMapping("/testMqProperties")
    public Result testMqProperties() {

        try {
            /** 1）direct */
            testMqDirectProducer.sendMsg("testExchangeDirectDefault", "", "test msg1... ");

            /** 2）work */
//            for (int i = 0; i < 10; i++) {
//                testMqDirectProducer.sendMsg("testExchangeWork1", "", "test msg2... " + i);
//            }
            /** 3）fanout */
//            testMqDirectProducer.sendMsg("testExchangeFanout1", "", "test msg3... ");
            /*for (int i = 1; i < 3; i++) {
                testMqDirectProducer.sendMsg("testExchangeFanout1", "", "test msg3... " + i);
            }*/
            /** 4）routing-direct */
//            testMqDirectProducer.sendMsg("testExchangeDirect1", "debug", "[debug]test msg5... ");
//            testMqDirectProducer.sendMsg("testExchangeDirect1", "warn", "[track]test msg5... ");
//            testMqDirectProducer.sendMsg("testExchangeDirect1", "info", "[info]test msg5... ");
//            testMqDirectProducer.sendMsg("testExchangeDirect1", "error", "[error]test msg5... ");
            /** 5）routing-topic */
//            testMqDirectProducer.sendMsg("testExchangeTopic1", "user.ccc", "[user.ccc]test msg4... ");
//            testMqDirectProducer.sendMsg("testExchangeTopic1", "user.save", "[user.save]test msg4... ");
//            testMqDirectProducer.sendMsg("testExchangeTopic1", "user.save.order", "[user.save.order]test msg4... ");
//            testMqDirectProducer.sendMsg("testExchangeTopic1", "aaa.bbb", "[aaa.bbb]test msg4... ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }


}
