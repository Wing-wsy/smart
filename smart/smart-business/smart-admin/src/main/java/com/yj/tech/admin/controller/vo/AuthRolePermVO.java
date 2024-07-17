package com.yj.tech.admin.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class AuthRolePermVO {
    private Long id;

    private List<Long> roles;

    private List<Long> perms;
}
