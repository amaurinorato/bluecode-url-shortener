package com.example.bluecode.urlshortener.service;

import com.example.bluecode.urlshortener.repository.UrlRepository;
import com.example.bluecode.urlshortener.dto.UrlShortenerDTO;
import com.example.bluecode.urlshortener.model.UrlModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShortenerService {

    @Value("${application.short-url-domain}")
    private String shortUrlDomain;

    Map<String, UrlModel> urls = new HashMap<>();

    @Autowired
    private UrlRepository urlRepository;

    public Mono<UrlShortenerDTO> findLongUrl(String encode) {
        return Mono.just(encode)
                .flatMap(urlRepository::findByShorten)
                .flatMap(x -> {
                    x.increaseAccess();
                    return urlRepository.updateByShorten(x.getShorten(), x.getAccess());
                })
                .map(x -> new UrlShortenerDTO(x.getLongVersion()))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<UrlShortenerDTO> shortenAndSave(String url) {
        //TODO - check if it exists before saving
        return encode(url)
                .flatMap(x -> urlRepository.save(new UrlModel(x, url, 0)))
                .map(x -> new UrlShortenerDTO(shortUrlDomain + x.getShorten()));
    }

    public Flux<UrlModel> getRanking() {
        return urlRepository.findTop100RankedByAccess();
    }

    private Mono<String> encode(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return Mono.just(hexString.substring(0, 16));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not hash input", e);
        }
    }
}
