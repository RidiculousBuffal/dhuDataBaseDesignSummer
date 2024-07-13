package com.dhu.swimmingpool.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "cos")
@PropertySource(value = "classpath:Cos.yml")
@Data
public class TecentConfig {
    private String SecretKey;
    private String SecretId;
    private String Region;
}
