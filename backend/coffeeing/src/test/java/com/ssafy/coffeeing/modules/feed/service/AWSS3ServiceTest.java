package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.modules.feed.dto.PresignedUrlResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AWSS3ServiceTest {

    @SpyBean
    private AWSS3Service awss3Service;


    @DisplayName("presignedUrl 생성 요청 시, 발급에 성공한다.")
    @Test
    void Given_RequestPresignedUrl_When_Generate_Then_Success() {

        //given
        ReflectionTestUtils.setField(awss3Service, "bucket", "test");
        ReflectionTestUtils.setField(awss3Service, "expiredIn", "1");
        ReflectionTestUtils.setField(awss3Service, "objectKey", "test");

        //when
        PresignedUrlResponse presignedUrlResponse = awss3Service.getPresignedUrlWithImagePath();

        //then
        assertThat(presignedUrlResponse.imagePath()).contains("test");
        assertThat(presignedUrlResponse.presignedUrl()).contains("test");
    }
}