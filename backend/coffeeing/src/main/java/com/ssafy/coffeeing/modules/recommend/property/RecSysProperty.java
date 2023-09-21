package com.ssafy.coffeeing.modules.recommend.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.annotation.PostConstruct;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "fast-api")
@ConstructorBinding
public class RecSysProperty {

    private final String baseUrl;
    private final String recByParamUrl;
    private final String recByProductUrl;
    private String collaborativeFilteringUrl;
    private String contentBasedFilteringUrl;

    @PostConstruct
    private void setUp() {
        collaborativeFilteringUrl = this.baseUrl.concat(this.recByParamUrl);
        contentBasedFilteringUrl = this.baseUrl.concat(this.recByProductUrl);
    }
}
