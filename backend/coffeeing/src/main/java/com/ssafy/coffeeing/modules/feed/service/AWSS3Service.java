package com.ssafy.coffeeing.modules.feed.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.ssafy.coffeeing.modules.feed.dto.PresignedUrlResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AWSS3Service {

    private final AmazonS3 amazonS3;
    private final String bucket;
    private final String expiredIn;
    private final String objectKey;

    public AWSS3Service(
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.s3.expire-in}") String expiredIn,
            @Value("${cloud.aws.s3.objectKey}") String objectKey,
            AmazonS3 amazonS3
    ) {
        this.bucket = bucket;
        this.expiredIn = expiredIn;
        this.objectKey = objectKey;
        this.amazonS3 = amazonS3;
    }

    public PresignedUrlResponse getPresignedUrlWithImagePath() {
        try {
            String imagePath = makeObjectKey();
            GeneratePresignedUrlRequest generatePresignedUrlRequest = createGeneratePresignedUrlRequestInstance(imagePath);
            String presignedUrl = generatePresignedUrlRequest(generatePresignedUrlRequest);
            return new PresignedUrlResponse(imagePath, presignedUrl);
        } catch (SdkClientException e) {
            throw new BusinessException(FeedErrorInfo.AWS_S3_CLIENT_EXCEPTION);
        }
    }

    private GeneratePresignedUrlRequest createGeneratePresignedUrlRequestInstance(String imagePath) {
        Date expiration = new Date();
        long expirationInMs = expiration.getTime();
        expirationInMs += Long.parseLong(expiredIn);
        expiration.setTime(expirationInMs);

        return new GeneratePresignedUrlRequest(bucket, imagePath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
    }

    private String generatePresignedUrlRequest(GeneratePresignedUrlRequest generatePresignedUrlRequest)
            throws SdkClientException {
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private String makeObjectKey() {
        return new StringBuffer().append(objectKey).append("/").append(UUID.randomUUID()).toString();
    }
}
