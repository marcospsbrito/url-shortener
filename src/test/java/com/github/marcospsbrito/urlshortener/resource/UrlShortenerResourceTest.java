package com.github.marcospsbrito.urlshortener.resource;

import com.github.marcospsbrito.urlshortener.UrlShortenerResource;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShortenerResourceTest {

    private static final String URL_MOCK = "http://google.com";
    @Autowired
    private UrlShortenerResource resource;

    @Test
    public void shouldReturnEmptyWhenGetWithoutRegister() {
        ShortUrlDTO dto = resource.get("aaaa").getBody();
        assertThat(dto).isEqualTo(null);
    }

    @Test
    public void shouldRegister() {
        ShortUrlDTO request = ShortUrlDTO.builder().url(URL_MOCK).build();
        ShortUrlDTO response = resource.post(request).getBody();
        assertThat(response).isNotNull();
    }

    @Test
    public void shouldNoDuplicateRegister() {
        ShortUrlDTO request = ShortUrlDTO.builder().url(URL_MOCK).build();

        ShortUrlDTO response = resource.post(request).getBody();
        ShortUrlDTO response2 = resource.post(request).getBody();

        assertThat(request).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response2).isNotNull();
        assertThat(request.getUrl()).isEqualTo(response.getUrl());
        assertThat(response.getKey()).isEqualTo(response2.getKey());
    }

    @Test
    public void shouldFindAfterRegister() {
        ShortUrlDTO request = ShortUrlDTO.builder().url(URL_MOCK).build();
        ShortUrlDTO postResponse = resource.post(request).getBody();
        ShortUrlDTO getResponse = resource.get(postResponse.getKey()).getBody();
        assertThat(postResponse).isNotNull();
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getUrl()).isEqualTo(URL_MOCK);
    }

}