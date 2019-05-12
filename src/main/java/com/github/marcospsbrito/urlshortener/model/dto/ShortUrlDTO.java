package com.github.marcospsbrito.urlshortener.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortUrlDTO {


    public static final String APP_HOST = "https://short-url-marcospsbrito.herokuapp.com/";

    public interface GetView{}
    public interface PostView{}

    @JsonView(GetView.class)
    private String url;

    private String key;

    @JsonView({PostView.class})
    public String getNewUrl(){
        return APP_HOST +key;
    }
}
