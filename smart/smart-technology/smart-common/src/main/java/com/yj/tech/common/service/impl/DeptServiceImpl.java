package com.yj.tech.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.tech.common.entity.Dept;
import com.yj.tech.common.mapper.DeptMapper;
import com.yj.tech.common.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * 部门 实现类
 *
 * @author zqd
 *
 * @date 2023-07-07 11:14:21
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
