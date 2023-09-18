package com.ssafy.coffeeing.modules.feed.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedUtil {

    private final ObjectMapper objectMapper;

    public FeedUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<ImageElement> makeJsonStringToImageElement(String imageUrl) {

        try {
            return objectMapper.readValue(imageUrl, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new BusinessException(FeedErrorInfo.FEED_IMAGE_ERROR);
        }
    }

    public String makeImageElementToJsonString(List<ImageElement> imageElements) {
        try{
            return objectMapper.writeValueAsString(imageElements);
        } catch (JsonProcessingException e) {
            throw new BusinessException(FeedErrorInfo.FEED_IMAGE_ERROR);
        }
    }
}
