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
