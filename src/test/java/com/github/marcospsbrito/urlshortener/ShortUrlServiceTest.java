package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.exceptions.ExpiredException;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlCreateDTO;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShortUrlServiceTest {

    private static final String KEY = "key";
    private static final String URL = "http://test.com";

    @InjectMocks
    private ShortUrlService service;

    @Mock
    private ShortUrlRepository repository;

    @Test
    public void find() throws ExpiredException {
        ShortUrl url = ShortUrl.builder()
                .key(KEY)
                .url(URL)
                .expiresAt(DateUtils.addMinutes(new Date(),5))
                .build();
        when(repository.findById(KEY)).thenReturn(Optional.of(url));

        ShortUrlDTO urlDTO = service.find(KEY);

        assertThat(urlDTO.getKey(), equalTo(url.getKey()));
        assertThat(urlDTO.getUrl(), equalTo(url.getUrl()));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotException() throws ExpiredException {
        when(repository.findById(KEY)).thenReturn(Optional.ofNullable(null));

        service.find(KEY);
    }

    @Test(expected = ExpiredException.class)
    public void shouldThrowExpiredException() throws ExpiredException {
        ShortUrl url = ShortUrl.builder()
                .key(KEY)
                .url(URL)
                .expiresAt(new Date())
                .build();
        when(repository.findById(KEY)).thenReturn(Optional.ofNullable(url));

        service.find(KEY);
    }

    @Test
    public void createShortUrl() throws MalformedURLException {
        ShortUrl shortUrl = ShortUrl.builder().url(URL).key(KEY).build();
        ShortUrlCreateDTO createDTO = ShortUrlCreateDTO.builder().url(URL).expiresInMinutes(10).build();
        when(repository.findByUrlNotExpired(eq(URL),any())).thenReturn(Optional.ofNullable(null));
        when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(repository.save(any())).thenReturn(shortUrl);

        ShortUrlDTO shortUrlDTO = service.createShortUrl(createDTO);

        verify(repository).findByUrlNotExpired(eq(URL),any());
        verify(repository).findById(anyString());
        verify(repository).save(any());
        assertThat(shortUrlDTO.getKey(), equalTo(shortUrl.getKey()));
        assertThat(shortUrlDTO.getUrl(), equalTo(shortUrl.getUrl()));
    }

    @Test
    public void createShortUrlWithExpirationDefaultTime() throws MalformedURLException {
        ShortUrl shortUrl = ShortUrl.builder().url(URL).key(KEY).build();
        ShortUrlCreateDTO createDTO = ShortUrlCreateDTO.builder().url(URL).build();
        when(repository.findByUrlNotExpired(eq(URL),any())).thenReturn(Optional.ofNullable(null));
        when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(repository.save(any())).thenReturn(shortUrl);

        ShortUrlDTO shortUrlDTO = service.createShortUrl(createDTO);

        verify(repository).findByUrlNotExpired(eq(URL),any());
        verify(repository).findById(anyString());
        verify(repository).save(any());
        assertThat(shortUrlDTO.getKey(), equalTo(shortUrl.getKey()));
        assertThat(shortUrlDTO.getUrl(), equalTo(shortUrl.getUrl()));
    }

    @Test
    public void shouldReturnExistent() throws MalformedURLException {
        ShortUrl shortUrl = ShortUrl.builder().url(URL).key(KEY).build();
        ShortUrlCreateDTO createDTO = ShortUrlCreateDTO.builder().url(URL).build();
        when(repository.findByUrlNotExpired(eq(URL),any())).thenReturn(Optional.ofNullable(shortUrl));

        ShortUrlDTO shortUrlDTO = service.createShortUrl(createDTO);

        verify(repository).findByUrlNotExpired(eq(URL),any());
        verify(repository, never()).findById(KEY);
        verify(repository, never()).save(any());
        assertThat(shortUrlDTO.getKey(), equalTo(shortUrl.getKey()));
        assertThat(shortUrlDTO.getUrl(), equalTo(shortUrl.getUrl()));
    }

}