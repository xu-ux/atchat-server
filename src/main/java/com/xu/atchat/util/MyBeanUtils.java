package com.xu.atchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MyBeanUtils {

    protected static final Logger logger = LoggerFactory.getLogger(MyBeanUtils.class);


    /**
     * 获取所有的属性值为空属性名数组
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

    /**
     * 检查bean中属性值是否全部为"" 或null
     *
     * @param object 检查对象
     * @return true 所有属性都为""或null时返回true
     */
    public static Boolean checkAllFieldsIsEmpty(Object object){
        if(null == object){
            return true;
        }else {
            Field[] fields = object.getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(object);
                    if (null != obj && !"".equals(obj)) {
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("校验bean中属性值是否全部为\"\" 或null时失败" + e.getMessage());
            }
            return true;
        }
    }

    /**
     * 检查bean中属性值是否全部为null
     *
     * @param object 检查对象
     * @return true 所有属性都为null时返回true
     */
    public static Boolean checkAllFieldsIsNull(Object object) {
        if(null == object){
            return true;
        }else {
            Field[] fields = object.getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(object);
                    if (null != obj ) {
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("校验bean中属性值是否全部null时失败"+e.getMessage());
            }
            return true;
        }
    }

    /**
     * 检查bean中属性值是否出现"" 或null
     *
     * @param object 检查对象
     * @return true 出现一个即为true
     */
    public static Boolean checkFieldsIsEmpty(Object object) {
        if(null == object){
            return true;
        }else {
            Field[] fields = object.getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(object);
                    if (null == obj || "".equals(obj)) {
                        return true;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("校验bean中属性值是否有\"\"或null时失败"+e.getMessage());
            }
            return false;
        }
    }






    /**
     * 检查bean中属性值是否为""或null
     * 自定义相关属性
     * @return true 出现一个即为true
     */
    public static Boolean checkCustomFieldsIsEmpty(Object object,String[] customFields) {
        if(null == object){
            return true;
        }else {
            Field[] fields = object.getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    for (String str : customFields){
                        if (str.equals(field.getName())){
                            Object obj = field.get(object);
                            if (null == obj || "".equals(obj)) {
                                return true;
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("自定义校验bean中属性值是否有\"\"或null时失败"+e.getMessage());
            }
            return false;
        }
    }
}
