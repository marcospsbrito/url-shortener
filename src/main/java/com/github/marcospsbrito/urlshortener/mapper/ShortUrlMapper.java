package com.github.marcospsbrito.urlshortener.mapper;

import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;

public class ShortUrlMapper {

    public static ShortUrlDTO toDTO(ShortUrl url) {
        return ShortUrlDTO.builder().key(url.getKey()).url(url.getUrl()).build();
    }

    public static ShortUrl toEntity(ShortUrlDTO urlDTO) {
        return ShortUrl.builder().key(urlDTO.getKey()).url(urlDTO.getUrl()).build();
    }
}
