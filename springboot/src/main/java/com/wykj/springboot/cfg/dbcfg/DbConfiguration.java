// package com.wykj.springboot.cfg.mysql;
//
// import com.baomidou.mybatisplus.annotation.IdType;
// import com.baomidou.mybatisplus.core.MybatisConfiguration;
// import com.baomidou.mybatisplus.core.config.GlobalConfig;
// import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
// import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
// import com.zaxxer.hikari.HikariDataSource;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.ibatis.session.ExecutorType;
// import org.mybatis.spring.mapper.MapperScannerConfigurer;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
// import org.springframework.core.io.Resource;
// import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
// import org.springframework.core.io.support.ResourcePatternResolver;
// import org.springframework.jdbc.datasource.DataSourceTransactionManager;
// import org.springframework.util.CollectionUtils;
//
// @Configuration
// @Slf4j
// public class DbConfiguration {
//
//     @Bean(name = "dataSourceProperties")
//     @Primary
//     @ConfigurationProperties(prefix = "spring.datasource.hikari")
//     public DataSourceProperties dataSourceProperties() {
//         return new DataSourceProperties();
//     }
//
//     @Bean(name = "p6DataSource")
//     @Primary
//     public HikariDataSource p6DataSource(@Qualifier("dataSourceProperties") DataSourceProperties properties) {
//         HikariDataSource dataSource = new HikariDataSource();
//         dataSource.setJdbcUrl(properties.getJdbcUrl());
//         dataSource.setUsername(properties.getUsername());
//         dataSource.setPassword(properties.getPassword());
//         dataSource.setDriverClassName(properties.getDriverClassName());
//         dataSource.setMaximumPoolSize(20);
//         return dataSource;
//     }
//
//
//     @Bean(name = "transactionManager")
//     @Primary
//     public DataSourceTransactionManager transactionManager(@Qualifier("p6DataSource") HikariDataSource p6DataSource) {
//         DataSourceTransactionManager bean = new DataSourceTransactionManager();
//         bean.setDataSource(p6DataSource);
//         return bean;
//     }
//
//     @Bean(name = "sqlSessionFactoryBean")
//     @Primary
//     public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("p6DataSource")HikariDataSource p6DataSource) {
//         MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//         bean.setDataSource(p6DataSource);
//         bean.setConfiguration(configuration());
//         bean.setGlobalConfig(globalConfig());
//         bean.setMapperLocations(resolveMapperLocations());
//         bean.setTypeAliasesPackage("org.csig.ads.workflow.entity");
//         //分页插件/ 乐观锁插件，只需要在更新对象里传递version 字段，同时字段要    @Version 注解。
//         // , new OptimisticLockerInterceptor()
//         // bean.setPlugins(new Interceptor[]{new PaginationInterceptor()});
//         return bean;
//     }
//
//     private Resource[] resolveMapperLocations() {
//         ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
//         List<String> mapperLocations = new ArrayList<>();
//         mapperLocations.add("classpath*:mapper/*Dao.xml");
//         List<Resource> resources = new ArrayList();
//         if (!CollectionUtils.isEmpty(mapperLocations)) {
//             for (String mapperLocation : mapperLocations) {
//                 try {
//                     Resource[] mappers = resourceResolver.getResources(mapperLocation);
//                     resources.addAll(Arrays.asList(mappers));
//                 } catch (IOException e) {
//                     log.error("Get myBatis resources happened exception", e);
//                 }
//             }
//         }
//         return resources.toArray(new Resource[resources.size()]);
//     }
//
//     @Bean(name = "configuration")
//     @Primary
//     public MybatisConfiguration configuration() {
//         MybatisConfiguration bean = new MybatisConfiguration();
//         bean.setMapUnderscoreToCamelCase(true);
//         bean.setCacheEnabled(true);
//         bean.setLazyLoadingEnabled(true);
//         bean.setUseColumnLabel(true);
//         bean.setDefaultExecutorType(ExecutorType.REUSE);
//         bean.setDefaultStatementTimeout(25000);
//         return bean;
//     }
//
//
//     @Bean(name = "globalConfig")
//     @Primary
//     public GlobalConfig globalConfig() {
//         GlobalConfig bean = new GlobalConfig();
//         bean.setDbConfig(dbConfig());
//         //逻辑删除，需要 deleteFLag 上有   @TableLogic 主机
//         // bean.setSqlInjector(new LogicSqlInjector());
//         return bean;
//     }
//
//
//     @Bean(name = "dbConfig")
//     @Primary
//     public DbConfig dbConfig() {
//         DbConfig bean = new DbConfig();
//         bean.setIdType(IdType.AUTO);
//         // bean.setKeyGenerator(new H2KeyGenerator());
//         return bean;
//     }
//
//     @Bean(name = "mapperScannerConfigurer")
//     @Primary
//     public MapperScannerConfigurer mapperScannerConfigurer() {
//         MapperScannerConfigurer bean = new MapperScannerConfigurer();
//         bean.setBasePackage("org.csig.ads.workflow.dao");
//         bean.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//         return bean;
//     }
// }
