package com.ssafy.coffeeing.modules.feed.dto;

public record PresignedUrlResponse(
        String imagePath,
        String imageUrl,
        String presignedUrl) {
}
