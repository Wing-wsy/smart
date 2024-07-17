package com.yj.tech.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.tech.common.entity.UserRole;
import com.yj.tech.common.mapper.UserRoleMapper;
import com.yj.tech.common.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户-角色 实现类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
