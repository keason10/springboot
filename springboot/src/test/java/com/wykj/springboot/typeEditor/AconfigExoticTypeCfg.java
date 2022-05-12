
package com.wykj.springboot.typeEditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AconfigExoticTypeCfg {

    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        //方式1
        // customEditorConfigurer.setCustomEditors(
        //         ImmutableMap.of(ExoticType.class, ExoticTypeEditor.class));

        //方式2
        customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{
                propertyEditorRegistry -> {
                    propertyEditorRegistry.registerCustomEditor(ExoticType.class, new ExoticTypeEditor());
                }
        });
        return customEditorConfigurer;
    }
}

