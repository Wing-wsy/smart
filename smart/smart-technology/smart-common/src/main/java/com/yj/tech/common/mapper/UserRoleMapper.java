package com.yj.tech.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.tech.common.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 *  用户-角色 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:21
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
