package com.yj.tech.admin.controller.system;


import com.yj.tech.admin.util.RouteTools;
import com.yj.tech.common.service.IMenuService;
import com.yj.tech.common.web.restful.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "路由")
@RestController
public class RouteController {

    @Autowired
    IMenuService menuService;

    @Autowired
    RouteTools routeTools;

    @Operation(summary = "构建路由")
    @GetMapping("/route/getRoutes")
    public Result getRoutes(){
        return Result.ok(routeTools.buildRouteTree());
    }
}
