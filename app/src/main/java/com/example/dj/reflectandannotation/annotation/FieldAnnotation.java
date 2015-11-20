package com.example.dj.reflectandannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DragonJiang
 * @date 2015/11/16
 * @Description:
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {
    /**
     * 关于隐私的注解的值。
     */
    int PRIVACY = 0;      //隐私的属性
    int PUBLIC = 1;       //公开的属性

    int privacyType() default PRIVACY;
}
