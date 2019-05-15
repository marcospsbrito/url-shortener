package com.github.marcospsbrito.urlshortener.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ShortUrlDTO {


    public static final String APP_HOST = "https://short-url-marcospsbrito.herokuapp.com/";

    public interface PostView{}

    private String url;

    private String key;

    private Date expiresAt;

    @JsonView({PostView.class})
    public String getNewUrl(){
        return APP_HOST +key;
    }
}
