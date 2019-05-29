package com.github.marcospsbrito.urlshortener.resource;

import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlCreateDTO;
import com.github.marcospsbrito.urlshortener.ShortUrlRepository;
import com.github.marcospsbrito.urlshortener.UrlShortenerResource;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShortenerResourceTest {

    private static final String URL_MOCK = "google.com";

    @Autowired
    private UrlShortenerResource resource;

    @Autowired
    private ShortUrlRepository repository;

    @Test
    public void shouldReturnNotFoundWhenGetWithoutRegister() {
        assertThat(resource.get("aaa").getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldRegister() {
        ShortUrlCreateDTO request = ShortUrlCreateDTO.builder().url(URL_MOCK).build();
        ShortUrlDTO response = resource.post(request).getBody();
        assertThat(response).isNotNull();
    }

    @Test
    public void shouldReturnBadRequestWhenMalformedURL() {
        ShortUrlCreateDTO request = ShortUrlCreateDTO.builder().url("$$MALFORMED$$").build();
        HttpStatus statusCode = resource.post(request).getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNoDuplicateRegister() {
        ShortUrlCreateDTO request = ShortUrlCreateDTO.builder().url(URL_MOCK).build();

        ShortUrlDTO response = resource.post(request).getBody();
        ShortUrlDTO response2 = resource.post(request).getBody();

        assertThat(request).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response2).isNotNull();
        assertThat(response2.getUrl()).endsWith(request.getUrl());
        assertThat(response.getKey()).isEqualTo(response2.getKey());
    }

    @Test
    public void shouldFindAfterRegister() {
        ShortUrlCreateDTO postBody = ShortUrlCreateDTO.builder().url(URL_MOCK).build();

        ShortUrlDTO postResponse = resource.post(postBody).getBody();
        HttpStatus statusCode = resource.get(postResponse.getKey()).getStatusCode();

        assertThat(postResponse).isNotNull();
        assertThat(statusCode).isEqualTo(HttpStatus.SEE_OTHER);
    }

    @Test
    public void shouldReturnStatusGones() {
        ShortUrl shortUrl = ShortUrl.builder().key("AAA").createAt(new Date()).expiresAt(new Date()).url(URL_MOCK).build();

        repository.save(shortUrl);
        HttpStatus statusCode = resource.get(shortUrl.getKey()).getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.GONE);
    }

}