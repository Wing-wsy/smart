package com.yj.tech.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.tech.common.entity.MenuPerm;
import org.apache.ibatis.annotations.Mapper;

/**
 *  菜单-权限 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-06 14:58:01
 */
@Mapper
public interface MenuPermMapper extends BaseMapper<MenuPerm> {

}
