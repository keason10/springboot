package com.wykj.springboot.utils;

import java.util.Optional;

public class StringUtil {

    /**
     * 返回多个字符串中第一个不为空字符的字符串
     * 如果没有，就返回null
     * @param array
     * @return
     */
    public static Optional colesceStr(String ... array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null || array[i].length() == 0) {
                continue;
            }
            return Optional.of(array[i]);
        }
        return Optional.empty();
    }
    public static Optional colesceObj(Object ... array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                continue;
            }
            return Optional.of(array[i]);
        }
        return Optional.empty();
    }
}
