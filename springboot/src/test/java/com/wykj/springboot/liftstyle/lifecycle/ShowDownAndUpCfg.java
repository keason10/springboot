package com.wykj.springboot.liftstyle.lifecycle;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShowDownAndUpCfg{


    @Bean
    public ShowDownAndUpBean showDownAndUpBean() {
        return new ShowDownAndUpBean();
    }

    // @Bean
    // public ShowDownAndUpBean2 showDownAndUpBean2() {
    //     return new ShowDownAndUpBean2();
    // }
}
