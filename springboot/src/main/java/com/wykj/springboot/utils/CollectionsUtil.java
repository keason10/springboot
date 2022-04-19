package com.wykj.springboot.utils;

import cn.hutool.core.util.ReflectUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

@Slf4j
public class CollectionsUtil {

    /**
     * 返回不报空指针的list
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> List<T> getSafeList(List<T> tList) {
        return Optional.ofNullable(tList).filter(CollectionUtils::isNotEmpty).orElse(Collections.emptyList());
    }


    /**
     * 自定义key的Map
     * @param list
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> listToMap(List<T> list, List<String> keys) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        Map<String, T> map = new HashMap<>();
        for (T entity : list) {
            StringBuffer unionKey = new StringBuffer();
            keys.forEach(key -> {
                Object val = ReflectUtil.getFieldValue(entity, key);
                unionKey.append(val);
                unionKey.append("_");
            });
            map.put(unionKey.toString(), entity);
        }
        return map;
    }


}
