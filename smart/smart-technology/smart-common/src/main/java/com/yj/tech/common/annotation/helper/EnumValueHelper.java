package com.yj.tech.common.annotation.helper;

import com.yj.tech.common.annotation.EnumValue;
import com.yj.tech.common.util.clazz.ClassUtil;

import java.lang.reflect.Field;

/**
 * 算法：获取枚举值对应的枚举常量
 */
public class EnumValueHelper {

    private EnumValueHelper() {
    }

    public static String getValue(Enum<?> enumConstant) {
        Field field = ClassUtil.getField(enumConstant);
        EnumValue ev = field.getAnnotation(EnumValue.class);
        if (ev != null) {
            return ev.value();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T valueOf(Class<T> enumClass, String value) {
        if (value != null) {
            Object[] enumConstants = enumClass.getEnumConstants();
            if (enumConstants != null) {
                for (Enum<?> enumConstant : (Enum<?>[]) enumConstants) {
                    Field field = ClassUtil.getField(enumConstant);
                    EnumValue ev = field.getAnnotation(EnumValue.class);
                    if (ev != null && value.trim().equals(ev.value())) {
                        return (T) enumConstant;
                    }
                }
            }
        }
        return null;
    }
}
