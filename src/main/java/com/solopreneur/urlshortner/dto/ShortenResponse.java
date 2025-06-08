package com.solopreneur.urlshortner.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@ToString
public class ShortenResponse {

    @JsonProperty("originalUrl")
    private String originalUrl;

    @JsonProperty("shortUrl")
    private String shortUrl;

    @JsonProperty("shortCode")
    private String shortCode;

    @JsonProperty("clickCount")
    private Long clickCount;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    // 기본 생성자
    public ShortenResponse() {
        this.success = true;
        this.message = "URL이 성공적으로 단축되었습니다.";
    }

    // 성공 응답용 생성자
    public ShortenResponse(String originalUrl, String shortUrl, String shortCode, Long clickCount, LocalDateTime createdAt) {
        this();
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

    // 에러 응답용 생성자
    public ShortenResponse(String errorMessage) {
        this.success = false;
        this.message = errorMessage;
    }

    // 정적 팩토리 메서드들
    public static ShortenResponse success(String originalUrl, String shortUrl, String shortCode, Long clickCount, LocalDateTime createdAt) {
        return new ShortenResponse(originalUrl, shortUrl, shortCode, clickCount, createdAt);
    }

    public static ShortenResponse error(String message) {
        return new ShortenResponse(message);
    }

    // Getter와 Setter
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
