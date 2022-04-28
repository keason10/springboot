package com.wykj.springboot.cfg.dbcfg;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceProperties {
    private String driverClassName;
    private String dataSourceClassName;
    private String jdbcUrl;
    private String username;
    private String password;
}
