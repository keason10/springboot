package com.wykj.springboot.utils;

import cn.hutool.core.util.StrUtil;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

/**
 * 摘要：Bean操作工具类(包括bean、列表拷贝)
 *
 * @author guohg03
 * @version 1.0
 * @Date 2016年12月21日
 */
@Slf4j
public class BeanUtil extends BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    @SuppressWarnings("rawtypes")
    private static Map<Class, List<Field>> fieldsMap = new HashMap<Class, List<Field>>();

    /**
     * 拷贝list列表(排除Null)
     *
     * @param sourceList 源列表
     * @param targetClass 目标class
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        if (null == sourceList || sourceList.size() == 0) {
            return null;
        }
        try {
            List<T> targetList = new ArrayList<T>(sourceList.size());
            for (Object source : sourceList) {
                T target = targetClass.newInstance();
                BeanUtil.copyNotNull(target, source);
                targetList.add(target);
            }
            return targetList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 拷贝Object(排除Null)
     *
     * @param target 目标
     * @param orig 源
     */
    public static <T> T copyNotNull(T target, Object orig) {
        if (null == target || null == orig) {
            return null;
        }
        return copy(target, orig, false);
    }

    /**
     * copy 带参数
     * @param target
     * @param orig
     * @param includedNull
     * @param ignoreProperties
     * @param <T>
     * @return
     */
    public static <T> T copy(T target, Object orig, boolean includedNull, String... ignoreProperties) {
        if (null == target || null == orig) {
            return null;
        }
        try {
            if (includedNull && ignoreProperties == null) {
                PropertyUtils.copyProperties(target, orig);
            } else {
                List<String> ignoreFields = new ArrayList<String>();
                if (ignoreProperties != null) {
                    for (String p : ignoreProperties) {
                        ignoreFields.add(p);
                    }
                }
                if (!includedNull) {
                    Map<String, String> parameterMap = BeanUtils.describe(orig);
                    for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                        if (StrUtil.isEmpty(entry.getValue())) {
                            String fieldName = entry.getKey();
                            if (!ignoreFields.contains(fieldName)) {
                                ignoreFields.add(fieldName);
                            }
                        }
                    }
                }
                org.springframework.beans.BeanUtils.copyProperties(orig, target,
                        ignoreFields.toArray(new String[ignoreFields.size()]));
            }
            return target;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        if (!ClassUtils.isCglibProxyClass(clazz)) {
            List<Field> result = fieldsMap.get(clazz);
            if (result == null) {
                result = getAllFieldList(clazz);
                fieldsMap.put(clazz, result);
            }
            return result;
        } else {
            return getAllFieldList(clazz);
        }
    }

    private static List<Field> getAllFieldList(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldList.add(field);
        }
        Class<?> c = clazz.getSuperclass();
        if (c != null) {
            fieldList.addAll(getAllFields(c));
        }
        return fieldList;
    }

    /**
     * Get field in class and its super class
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> c = clazz.getSuperclass();
        if (c != null) {
            return getField(c, fieldName);
        }
        return null;
    }

    /**
     * 字符数组合并 注：接受多个数组参数的输入，合并成一个数组并返回。
     *
     * @param arr 输入的数组参数，一个或若干个
     * @return
     */
    public static String[] getMergeArray(String[]... arr) {
        if (arr == null) {
            return null;
        }
        int length = 0;
        for (Object[] obj : arr) {
            length += obj.length;
        }
        String[] result = new String[length];
        length = 0;
        for (int i = 0; i < arr.length; i++) {
            System.arraycopy(arr[i], 0, result, length, arr[i].length);
            length += arr[i].length;
        }
        return result;
    }

    public static <T> T clone(Object obj, Class<T> targetClazz) {
        try {
            return copy(targetClazz.newInstance(), obj, false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 功能：将bean对象转换为url参数
     */
    public static String bean2urlparam(Object obj) {
        try {
            StringBuilder builder = new StringBuilder();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String propKey = property.getName();
                if (!"class".equals(propKey)) {
                    Object propVal = PropertyUtils.getProperty(obj, propKey);
                    if (propVal != null && StrUtil.isNotEmpty(propVal.toString())) {
                        builder.append(propKey).append("=").append(propVal).append("&");
                    }
                }
            }
            if (builder.length() > 0) {
                return builder.deleteCharAt(builder.length() - 1).toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 功能：将url中的对应obj中的属性参数值替换
     */
    public static String bean2urlparam(Object obj, String param) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            String newStr = param;
            for (PropertyDescriptor property : propertyDescriptors) {
                String propKey = property.getName();
                if (!"class".equals(propKey)) {
                    Object propVal = PropertyUtils.getProperty(obj, propKey);
                    if (propVal != null && StrUtil.isNotEmpty(propVal.toString())) {
                        newStr = newStr.replaceAll("=" + propKey, "=" + propVal.toString());
                    }
                }
            }
            return newStr;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 功能：将url中的对应obj和map中的属性参数值替换
     */
    public static String bean2urlparam(Object obj, String param, Map<String, Object> plus) {
        try {
            String newParam = bean2urlparam(obj, param);
            if (MapUtils.isNotEmpty(plus)) {
                for (Map.Entry<String, Object> entry : plus.entrySet()) {
                    Object propVal = entry.getValue();
                    if (propVal != null && newParam != null
                            && StrUtil.isNotEmpty(propVal.toString())) {
                        newParam = newParam.replaceAll("=" + entry.getKey(), "=" + propVal.toString());
                    }
                }
            }
            if (newParam != null && newParam.indexOf('#') != -1) {
                newParam = newParam.replaceAll("#", "");
            }
            return newParam;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 功能：将URL参数转换为map
     */
    public static Map<String, String> urlparam2map(String param) {
        if (StrUtil.isNotEmpty(param)) {
            Map<String, String> map = null;
            List<String> arr = StrUtil.split(param, "&");
            if (arr != null && arr.size() > 0) {
                map = new HashMap<String, String>();
                for (String item : arr) {
                    String[] eqArr = item.split("=");
                    map.put(eqArr[0], eqArr[1]);
                }
                return map;
            }
        }
        return null;
    }


    /**
     * 将一个 Map 对象转化为一个 JavaBean
     */
    @SuppressWarnings("rawtypes")
    public static <T> T toBean(Class<T> clazz, Map map) {
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // 创建 JavaBean 对象

            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);
                    if ("".equals(value)) {
                        value = null;
                    }
                    Object[] args = new Object[1];
                    args[0] = value;
                    try {
                        if (descriptor.getPropertyType() != null && descriptor.getPropertyType()
                                .equals(Integer.class)) {
                            String valueStr = map.get(propertyName).toString();
                            if (StrUtil.isNotEmpty(valueStr)) {
                                valueStr = "1";
                            }
                            Integer valueInteger = Integer.valueOf(valueStr);
                            args[0] = valueInteger;
                            descriptor.getWriteMethod().invoke(obj, args);
                        } else {
                            descriptor.getWriteMethod().invoke(obj, args);
                        }
                    } catch (InvocationTargetException e) {
                        logger.info("字段映射失败！" + e.getMessage(), e);
                    }
                }
            }
            return (T) obj;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}