package com.yj.tech.common.annotation;

import com.yj.tech.constant.basic.Strings;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明注解，用于注释、说明、解释
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Captions.class)
public @interface Caption {
    /**
     * 说明文本
     *
     * @return 说明文本
     */
    String value();

    String locale() default Strings.EMPTY;

}
