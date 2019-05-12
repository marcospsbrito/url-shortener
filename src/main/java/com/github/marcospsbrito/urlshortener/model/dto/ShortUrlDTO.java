package com.github.marcospsbrito.urlshortener.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortUrlDTO {
    private String url;
    private String key;
}
