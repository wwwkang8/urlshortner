package com.solopreneur.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShortenRequest {

    @NotBlank(message = "URL은 필수입니다")
    @Pattern(regexp = "^https?://.*", message = "올바른 URL 형식이 아닙니다")
    private String originalUrl;

    // 기본 생성자
    public ShortenRequest() {}

    // 생성자
    public ShortenRequest(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    // Getter와 Setter
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

}
