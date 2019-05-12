package com.github.marcospsbrito.urlshortener.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortUrlDTO {


    public interface get{}
    public interface post{}

    @JsonView(ShortUrlDTO.post.class)
    private String url;

    @JsonView(ShortUrlDTO.post.class)
    private String key;

    @JsonView({ShortUrlDTO.post.class,ShortUrlDTO.get.class})
    public String getNewUrl(){
        return "http://localhost:8081/"+key;
    }
}
