package com.github.marcospsbrito.urlshortener.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortUrlCreateDTO {
    private String url;
    private int expiresInMinutes;
}
