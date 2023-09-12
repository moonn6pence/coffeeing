package com.ssafy.coffeeing.modules.feed.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.ssafy.coffeeing.modules.feed.dto.PresignedUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class AWSS3Service {

    private final AmazonS3 amazonS3;
    private final String bucket;
    private final String expireIn;
    private final String objectKey;

    public AWSS3Service(
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.s3.expire-in}") String expiredIn,
            @Value("${cloud.aws.s3.objectKey}") String objectKey,
            AmazonS3 amazonS3
    ) {
        this.bucket = bucket;
        this.expireIn = expiredIn;
        this.objectKey = objectKey;
        this.amazonS3 = amazonS3;
    }

    public PresignedUrlResponse getPresignedUrlWithImagePath() {
        String imagePath = makeObjectKey();
        String presignedUrl = generatePresignedUrlRequest(imagePath);
        return new PresignedUrlResponse(imagePath, presignedUrl);
    }
    
    private String generatePresignedUrlRequest(String imagePath) {
        Date expiration = new Date();
        long expirationInMs = expiration.getTime();
        expirationInMs += Long.parseLong(expireIn);
        expiration.setTime(expirationInMs);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, imagePath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private String makeObjectKey() {
        return new StringBuffer().append(objectKey).append("/").append(UUID.randomUUID()).toString();
    }
}
