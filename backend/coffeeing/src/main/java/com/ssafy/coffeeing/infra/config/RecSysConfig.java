package com.ssafy.coffeeing.infra.config;

import com.ssafy.coffeeing.modules.recommend.property.RecSysProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RecSysProperty.class)
public class RecSysConfig {
}
