package com.yj.tech.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yj.tech.admin.util.Tools;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 通用字段填充
 */
@Component
public class MyBatisPlusFieldConfig implements MetaObjectHandler {

    /**
     * 使用mp做添加操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置属性值
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("deleteStatus",1,metaObject);
        if(Tools.getUsername()!=null){
            this.setFieldValByName("createBy", Tools.getUsername(),metaObject);
            this.setFieldValByName("updateBy", Tools.getUsername(),metaObject);
        }
    }

    /**
     * 使用mp做修改操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy", Tools.getUsername(),metaObject);
    }
}
