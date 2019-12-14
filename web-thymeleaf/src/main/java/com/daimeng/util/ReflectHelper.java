package com.daimeng.util;

import java.lang.reflect.Field;


/**
 * 
* @功能描述: 常用反射工具类
* @名称: ReflectHelper.java 
* @路径 com.daimeng.util 
* @作者 daimeng@tansun.com.cn
* @创建时间 2019年4月20日 下午2:52:22 
* @version V1.0
 */
public class ReflectHelper {
    /**
     * 获取obj对象fieldName的Field
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if(field!=null){
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName,
            Object value) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
    
    /**
     * 测试主函数
     * @param args
     */
    public static void main(String[] args) {
        try {
            Class cls = Class.forName("com.flf.entity.User");
            Field field;
            try {
                // 得到一个类的实例
                Object user = cls.newInstance();
                // 调用根据字段名得到字段的方法
                field = ReflectHelper.getFieldByFieldName(user, "loginname");
                Constants.println(field.getName());
                
                 // 根据字段名给字段赋值
                ReflectHelper.setValueByFieldName(user, "loginname","admin");
                 
                // 根据字段名获取到字段值
                Object nameValue = ReflectHelper.getValueByFieldName(user, "loginname");
                Constants.println(nameValue);
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public static Object getObjectForName(String className){
    	Class clazz = null;
        try {
            clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
