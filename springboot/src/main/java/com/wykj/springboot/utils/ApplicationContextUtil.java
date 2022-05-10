package com.wykj.springboot.utils;

import cn.hutool.core.io.IoUtil;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
public class ApplicationContextUtil  implements ApplicationContextAware, EnvironmentAware{

    private static ApplicationContext ctx;
    private static Environment env;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }


    /**
     * 返回不为空，否则抛出异常
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> tClass) {
        T ctxBean = ctx.getBean(tClass);
        if (ctxBean ==null) {
            throw new RuntimeException(String.format("根据[%s]在spring上下文未获取到Bean", tClass.toString()));
        }
        return ctxBean;
    }

    /**
     * 返回不为空，否则抛出异常
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T getBean(String className,Class<T> tClass) {
        T ctxBean = ctx.getBean(className, tClass);
        if (ctxBean ==null) {
            throw new RuntimeException(String.format("根据[%s]在spring上下文未获取到Bean", className + "@" + tClass));
        }
        return ctxBean;
    }



    /**
     * 可能返回Optional.value 的值为空
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> Optional<T> getValue(String key, Class<T> tClass) {
        if (!env.containsProperty(key)) {
            throw new RuntimeException(String.format("根据 %s 在spring上下文未获取到属性", key));
        }
        return Optional.ofNullable(env.getProperty(key, tClass));
    }


    /**
     * 获取spring上下文中的Resources
     *          Resource[] resources = ctx.getResources("classpath:*.yml");
     *          参照接口 {@link org.springframework.core.io.support.ResourcePatternResolver#getResources(java.lang.String)}
     *          @link {Ant-style path patterns:
     *                       ? 匹配任何单字符
     *                       * 匹配0或者任意数量的字符
     *                       ** 匹配0或者更多的目录
     *                       https://www.cnblogs.com/selfchange/p/spring.html
     *                   }
     *
     * Prefix  Example	Explanation
     *          classpath:com/myapp/config.xml
     *          file:///data/config.xml
     *          https://myserver/logo.png
     * @param filePath
     * @return fileText
     */
    @SneakyThrows
    public static String getResourceText(String filePath){
        Resource resource = ctx.getResource(filePath);
        if (resource == null || !resource.exists()) {
            throw new RuntimeException("file path[%s] 不存在");
        }
        InputStream inputStream = resource.getInputStream();
        return IoUtil.readUtf8(inputStream);
    }


    @SneakyThrows
    public static List<PropertySource<?>> loadProperties(String filePath) {
        String fileTail = filePath.substring(filePath.lastIndexOf(".") + 1);
        PropertySourceLoader propertiesPropertySourceLoader = new PropertiesPropertySourceLoader();
        if (Arrays.asList(propertiesPropertySourceLoader.getFileExtensions()).contains(fileTail)) {
            return propertiesPropertySourceLoader.load("dotProperties", ctx.getResource(filePath));
        }
        propertiesPropertySourceLoader = new YamlPropertySourceLoader();
        if (Arrays.asList(propertiesPropertySourceLoader.getFileExtensions()).contains(fileTail)) {
            return propertiesPropertySourceLoader.load("ymlProperties", ctx.getResource(filePath));
        }
        throw new Exception("不支持的文件类型");
    }
}

