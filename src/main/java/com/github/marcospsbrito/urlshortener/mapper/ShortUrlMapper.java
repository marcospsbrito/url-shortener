package com.github.marcospsbrito.urlshortener.mapper;

import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;

public class ShortUrlMapper {

    public static ShortUrlDTO toDTO(ShortUrl url) {
        return ShortUrlDTO.builder()
                .url(url.getUrl())
                .key(url.getKey())
                .expiresAt(url.getExpiresAt())
                .build();
    }
}