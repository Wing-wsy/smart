package com.yj.tech.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.tech.common.entity.Role;
import com.yj.tech.common.mapper.RoleMapper;
import com.yj.tech.common.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色 实现类
 *
 * @author zqd
 *
 * @date 2023-07-05 13:47:02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getSelectedRolesByMenuId(Long menuId) {
        return roleMapper.getSelectedRolesByMenuId(menuId);
    }

    @Override
    public List<Role> getSelectedRolesByUserId(Long userId) {
        return roleMapper.getSelectedRolesByUserId(userId);
    }
}
