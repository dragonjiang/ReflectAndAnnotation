# ReflectAndAnnotation
java的 Reflect、Annotation 反射 和 注解的使用
# 反射 #
##  ##
java的反射机制运行程序在运行时通过反射的APIs取得class的内部信息。
关于反射的文字的说明可以看另一篇博文[http://blog.csdn.net/renyuanchunby/article/details/7052895](http://blog.csdn.net/renyuanchunby/article/details/7052895 "java 反射")

本文主要从应用的角度讲解反射。

先写一个Bean类,定义int型的age, String name, List beans。

    package com.example.dj.reflectandannotation;
    
    import java.util.List;
    
    /**
     * @author DragonJiang
     * @date 2015/11/16
     * @Description:
     */
    public class Bean {
        private int age;
    
        public String name;
    
        List<Bean> beans;
    
        public int getAge() {
            return age;
        }
    
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




然后在BeanTest类进行测试：

    
    package com.example.dj.reflectandannotation;
    
    import android.util.Log;
    
    import java.lang.reflect.Field;
    import java.lang.reflect.ParameterizedType;
    import java.lang.reflect.Type;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;
    
    /**
     * @author DragonJiang
     * @date 2015/11/16
     * @Description:
     */
    public class BeanTest {
    
        private static final String TAG = "BeanTest";
    
        public static void testBean(){
			//定义一个bean的实体，然后初始化
            Bean bean = new Bean();
            List<Bean> beans = new ArrayList<>();
            beans.add(bean);
            bean.setAge(12);
            bean.setName("dragon");
            bean.setBeans(beans);
    
            Class clazz = bean.getClass();//取得类的对象
    
            Field[]  fields = clazz.getDeclaredFields();//取得类中所有属性的集合
    
            try {
                for (Field field : fields) {
                    if (!field.isAccessible()) {//如果这些属性是不可访问的，设为可访问
                        field.setAccessible(true);
                    }
    
                    Class fieldType = field.getType();//取得该属性的类型
                    String strType = fieldType.toString();
    
                    if(int.class.isAssignableFrom(fieldType)){//判断该属性是否int类型，取得属性值
                        int i = (int) field.get(bean);
                        Log.d(TAG, strType + ", " + field.getName() + " = "+ i);
    
                    } else if(String.class.isAssignableFrom(fieldType)){//判断该属性是否String类型，取得属性值
                        String s = (String) field.get(bean);
                        Log.d(TAG, strType + ", " + field.getName() + " = "+ s);
    
                    } else if(List.class.isAssignableFrom(fieldType)){//判断该属性是否List类型,如果是，取得List的参数类型
                        Type type = field.getGenericType();//取得该属性的参数类型
    
                        if(type instanceof ParameterizedType){
                            Class itemClazz = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];//取得list的实际参数类型
    
                            if(Bean.class.isAssignableFrom(itemClazz)){//判断类型是否Bean类型
                                List list = (List) field.get(bean);//取得List
                                Iterator it = list.iterator();
                                while (it.hasNext()){
                                    Bean b = (Bean) it.next();//取得Bean
                                    Log.d(TAG, strType + ", " + field.getName() + "; itemType = "
                                            + itemClazz.getSimpleName() + "， value : " + b.toString());
                                }
                            }
                        }
    
                    }
                }
    
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

运行BeanTest.testBean();
打印出来的log如下：
    
    11-20 11:23:34.750 7652-7652/? D/BeanTest: class java.lang.String, name = dragon
    11-20 11:23:34.750 7652-7652/? D/BeanTest: interface java.util.List, beans; itemType = Bean， value : name = dragon, age = 12
    11-20 11:23:34.750 7652-7652/? D/BeanTest: int, age = 12
通过反射分别取到了Bean定义的三个属性值。举一反三可以同样的用反射调用类内部的方法。

# 注解 #
##  ##
注解在安卓的开发中可以提供很大的便利。

注解有元注解、自定义注解。元注解是的作用就是注解其他注解。
关于注解的说明可以看另一篇博文：[http://blog.csdn.net/longlongxy/article/details/44414237](http://blog.csdn.net/longlongxy/article/details/44414237 "android 注解")

定义三个注解类，分别是列注解、方法注解、属性注解

类注解：

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
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ClassAnnotation {
    }


方法注解：


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
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MethodAnnotation {
    }


属性注解：

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

然后对Bean类进行修改，增加注解：

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


在AnnotationTest类解析注解：

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

运行AnnotationTest.testAnnotation();打印log：

    11-20 11:23:34.750 7652-7652/? D/AnnotationTest: the class Bean has ClassAnnotation : @com.example.dj.reflectandannotation.annotation.ClassAnnotation()
    11-20 11:23:34.750 7652-7652/? D/AnnotationTest: the method setAge has MethodAnnotation : @com.example.dj.reflectandannotation.annotation.MethodAnnotation()
    11-20 11:23:34.754 7652-7652/? D/AnnotationTest: name has FieldAnnotation : privacyType == FieldAnnotation.PUBLIC
    11-20 11:23:34.754 7652-7652/? D/AnnotationTest: beans has FieldAnnotation : privacyType == FieldAnnotation.PRIVACY
    11-20 11:23:34.754 7652-7652/? D/AnnotationTest: age has FieldAnnotation : privacyType == FieldAnnotation.PRIVACY