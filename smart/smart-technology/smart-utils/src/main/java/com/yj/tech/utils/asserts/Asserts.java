package com.yj.tech.utils.asserts;


/**
 * 断言
 */
public class Asserts {

    public static void isTrue(boolean b,String msg)  {
        if(b){
            throw new RuntimeException(msg);
        }
    }

    public static void isNull(Object obj,String msg){
        if(obj == null){
            throw new RuntimeException(msg);
        }
    }

    public static void isNotNull(Object obj, String msg) {
        if(obj != null){
            throw new RuntimeException(msg);
        }
    }

}
