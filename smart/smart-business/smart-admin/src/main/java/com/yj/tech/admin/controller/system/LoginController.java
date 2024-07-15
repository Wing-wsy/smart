package com.yj.tech.admin.controller.system;

import com.yj.tech.common.web.restful.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理系统登录控制器
 *
 * @author fangen
 * @since 2023-05-27
 **/
@Tag(name = "系统登录控制器")
@RestController
public class LoginController {

    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password,
                        @RequestParam(required = false) boolean checked) {
        // 用于安全认证登录引导，无需处理任何逻辑
        return Result.success(username + password + checked);
    }

    @PostMapping("/logout")
    public Result logout() {
        // 用于登出流程引导，无需处理任何逻辑
        return Result.success();
    }

}
