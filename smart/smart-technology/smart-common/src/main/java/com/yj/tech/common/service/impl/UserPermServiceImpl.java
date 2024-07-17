package com.yj.tech.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.tech.common.entity.UserPerm;
import com.yj.tech.common.mapper.UserPermMapper;
import com.yj.tech.common.service.IUserPermService;
import org.springframework.stereotype.Service;

/**
 * 用户-权限 实现类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:04
 */
@Service
public class UserPermServiceImpl extends ServiceImpl<UserPermMapper, UserPerm> implements IUserPermService {

}
