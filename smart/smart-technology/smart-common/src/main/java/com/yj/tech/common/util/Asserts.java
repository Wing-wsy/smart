package com.yj.tech.common.util;

import cn.hutool.core.collection.CollUtil;
import com.yj.tech.common.entity.Menu;
import com.yj.tech.common.entity.Role;
//import org.springframework.security.authorization.AuthorizationDecision;

import java.util.List;

/**
 * 断言
 */
public class Asserts {

    public static void isTrue(boolean b,String msg)  {
        if(b){
            throw new RuntimeException(msg);
        }
    }

    public static void isNull(Object obj,String msg){
        if(obj == null){
            throw new RuntimeException(msg);
        }
    }

    public static void isNotNull(Object obj, String msg) {
        if(obj != null){
            throw new RuntimeException(msg);
        }
    }

//    public static void isGuest(List<Menu> menus, AuthorizationDecision authorizationDecision) {
//
//    }
//
//
//    public boolean isGuest(List<Menu> menus){
//
//        for (Menu menu : menus) {
//            if(CollUtil.isNotEmpty(menu.getRoles())){
//                for (Role role : menu.getRoles()) {
//                    if(role.getTag().equalsIgnoreCase("GUEST")){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
}
