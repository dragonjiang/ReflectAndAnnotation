package com.example.dj.reflectandannotation;

import android.util.Log;

import com.example.dj.reflectandannotation.annotation.ClassAnnotation;
import com.example.dj.reflectandannotation.annotation.FieldAnnotation;
import com.example.dj.reflectandannotation.annotation.MethodAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DragonJiang
 * @date 2015/11/16
 * @Description:
 */
public class AnnotationTest {
    private static final String TAG = "AnnotationTest";

    public static void testAnnotation(){
        Bean bean = new Bean();
        List<Bean> beans = new ArrayList<>();
        beans.add(bean);
        bean.setAge(12);
        bean.setName("dragon");
        bean.setBeans(beans);

        //类注解
        Class clazz = bean.getClass();//取得类的对象
        ClassAnnotation ca = (ClassAnnotation) clazz.getAnnotation(ClassAnnotation.class);//取类的注解
        if(ca != null) {
            Log.d(TAG, "the class "+ clazz.getSimpleName() + " has ClassAnnotation : " + ca.toString());//打印注解
        }

        //方法注解
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods){
            MethodAnnotation ma = method.getAnnotation(MethodAnnotation.class);
            if(ma != null){
                Log.d(TAG, "the method " + method.getName() + " has MethodAnnotation : " + ma.toString());//打印注解
            }
        }

        //属性注解
        Field[]  fields = clazz.getDeclaredFields();//取得类中所有属性的集合
        for (Field field : fields) {
            FieldAnnotation fa = field.getAnnotation(FieldAnnotation.class);
            if(fa == null){
                continue;
            }

            int privacyType = fa.privacyType();
            if(privacyType == FieldAnnotation.PRIVACY){
                Log.d(TAG, field.getName() + " has FieldAnnotation : " + "privacyType == FieldAnnotation.PRIVACY");
            } else {
                Log.d(TAG, field.getName() + " has FieldAnnotation : " + "privacyType == FieldAnnotation.PUBLIC");
            }
        }
    }
}
