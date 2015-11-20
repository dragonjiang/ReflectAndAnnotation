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
     * 注解的参数privacyType的值。
     */
    int PRIVACY = 0;      //隐私的属性
    int PUBLIC = 1;       //公开的属性

    int privacyType() default PRIVACY;
}
