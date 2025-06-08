package com.solopreneur.urlshortner.controller;

import com.solopreneur.urlshortner.dto.ShortenRequest;
import com.solopreneur.urlshortner.dto.ShortenResponse;
import com.solopreneur.urlshortner.entity.UrlMapping;
import com.solopreneur.urlshortner.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 개발용 - 실제 운영에서는 특정 도메인만 허용
@Slf4j
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@Valid @RequestBody ShortenRequest request) {
        try {
            UrlMapping urlMapping = urlShortenerService.shortenUrl(request.getOriginalUrl());
            String shortUrl = urlShortenerService.getShortUrl(urlMapping.getShortCode());

            log.info("originalurl : " + urlMapping.getOriginalUrl());
            log.info("shorturl : " + shortUrl);
            log.info("shortcode : " + urlMapping.getShortCode());
            log.info("clickcount : " + urlMapping.getClickCount());

            ShortenResponse response = ShortenResponse.success(
                urlMapping.getOriginalUrl(),
                shortUrl,
                urlMapping.getShortCode(),
                urlMapping.getClickCount(),
                urlMapping.getCreatedAt()
            );
            log.info(response.getOriginalUrl());
            log.info(response.getShortUrl());
            log.info(response.getShortCode());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error shortening URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/s/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);

        if (originalUrl != null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(originalUrl);
            return redirectView;
        } else {
            // 404 페이지로 리다이렉트하거나 에러 페이지 표시
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/error");
            return redirectView;
        }
    }

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<UrlMapping> getStats(@PathVariable String shortCode) {
        UrlMapping urlMapping = urlShortenerService.getUrlMappingByShortCode(shortCode);

        if (urlMapping != null) {
            return ResponseEntity.ok(urlMapping);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
