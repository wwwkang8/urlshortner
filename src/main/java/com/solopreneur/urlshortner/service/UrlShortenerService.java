package com.solopreneur.urlshortner.service;

import java.util.Optional;
import java.util.Random;

import com.solopreneur.urlshortner.entity.UrlMapping;
import com.solopreneur.urlshortner.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;
    private final String BASE_URL = "http://localhost:8080/s/";
    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_CODE_LENGTH = 6;
    private final Random random = new Random();

    @Autowired
    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping shortenUrl(String originalUrl) {
        // 기존에 단축된 URL이 있는지 확인
        Optional<UrlMapping> existingMapping = urlMappingRepository.findByOriginalUrl(originalUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get();
        }

        // 새로운 단축 코드 생성
        String shortCode = generateUniqueShortCode();
        UrlMapping urlMapping = new UrlMapping(originalUrl, shortCode);

        return urlMappingRepository.save(urlMapping);
    }

    public String getOriginalUrl(String shortCode) {
        Optional<UrlMapping> urlMapping = urlMappingRepository.findByShortCode(shortCode);
        if (urlMapping.isPresent()) {
            UrlMapping mapping = urlMapping.get();
            mapping.incrementClickCount();
            urlMappingRepository.save(mapping);
            return mapping.getOriginalUrl();
        }
        return null;
    }

    public UrlMapping getUrlMappingByShortCode(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode).orElse(null);
    }

    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = generateRandomString();
        } while (urlMappingRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public String getShortUrl(String shortCode) {
        return BASE_URL + shortCode;
    }

}
