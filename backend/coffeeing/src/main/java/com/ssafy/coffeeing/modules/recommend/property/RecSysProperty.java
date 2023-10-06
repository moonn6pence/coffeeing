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
    private final String preferencePath;
    private final String similarityPath;
    private final String criteriaPath;
    private String pickByPreferenceUrl;
    private String pickBySimilarityUrl;
    private String pickByCriteriaUrl;

    @PostConstruct
    private void setUp() {
        pickByPreferenceUrl = this.baseUrl.concat(this.preferencePath);
        pickBySimilarityUrl = this.baseUrl.concat(this.similarityPath);
        pickByCriteriaUrl = this.baseUrl.concat(this.criteriaPath);
    }
}
