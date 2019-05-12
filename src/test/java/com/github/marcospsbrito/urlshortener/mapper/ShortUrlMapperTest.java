package com.github.marcospsbrito.urlshortener.mapper;

import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ShortUrlMapperTest {

    @Test
    public void toDTO() {
        ShortUrl url = ShortUrl.builder().createAt(new Date()).key("k").url("url").build();
        ShortUrlDTO urlDTO = ShortUrlMapper.toDTO(url);
        assertThat(urlDTO.getUrl(), equalTo(url.getUrl()));
        assertThat(urlDTO.getKey(), equalTo(url.getKey()));
    }

    @Test
    public void shouldBeEmptyDTO() {
        ShortUrl url = ShortUrl.builder().createAt(new Date()).build();
        ShortUrlDTO urlDTO = ShortUrlMapper.toDTO(url);
        assertThat(urlDTO.getUrl(), equalTo(url.getUrl()));
        assertThat(urlDTO.getKey(), equalTo(url.getKey()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenNull() {
        ShortUrl url = null;
        ShortUrlMapper.toDTO(url);
    }
}