package com.yj.tech.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.tech.common.entity.MenuPerm;
import com.yj.tech.common.mapper.MenuPermMapper;
import com.yj.tech.common.service.IMenuPermService;
import org.springframework.stereotype.Service;

/**
 * 菜单-权限 实现类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:58:01
 */
@Service
public class MenuPermServiceImpl extends ServiceImpl<MenuPermMapper, MenuPerm> implements IMenuPermService {

}
