package com.dhu.swimmingpool.Bean;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class Tencent {
    @Bean
    public PropertySourcesPlaceholderConfigurer getTencentConfig(){
        PropertySourcesPlaceholderConfigurer configurer =
            new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean config_Bean = new YamlPropertiesFactoryBean();
        config_Bean.setResources(new ClassPathResource("Cos.yml"));
        configurer.setProperties(config_Bean.getObject());
        return configurer;
    }
}
