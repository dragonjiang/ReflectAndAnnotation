package com.example.dj.reflectandannotation;

import com.example.dj.reflectandannotation.annotation.ClassAnnotation;
import com.example.dj.reflectandannotation.annotation.FieldAnnotation;
import com.example.dj.reflectandannotation.annotation.MethodAnnotation;

import java.util.List;

/**
 * @author DragonJiang
 * @date 2015/11/16
 * @Description:
 */
@ClassAnnotation
public class Bean {
    @FieldAnnotation(privacyType = FieldAnnotation.PRIVACY)
    private int age;

    @FieldAnnotation(privacyType = FieldAnnotation.PUBLIC)
    public String name;

    @FieldAnnotation(privacyType = FieldAnnotation.PRIVACY)
    List<Bean> beans;

    public int getAge() {
        return age;
    }

    @MethodAnnotation
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    @Override
    public String toString() {
        return "name = " + name + ", age = " + age;
    }
}
