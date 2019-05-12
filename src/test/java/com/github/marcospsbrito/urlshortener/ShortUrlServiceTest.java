package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShortUrlServiceTest {

    private static final String KEY = "key";
    private static final String URL = "url";

    @InjectMocks
    private ShortUrlService service;

    @Mock
    private ShortUrlRepository repository;

    @Test
    public void find() {
        ShortUrl url = ShortUrl.builder().key(KEY).url(URL).build();
        when(repository.findById(KEY)).thenReturn(Optional.of(url));

        ShortUrlDTO urlDTO = service.find(KEY);

        assertThat(urlDTO.getKey(), equalTo(url.getKey()));
        assertThat(urlDTO.getUrl(), equalTo(url.getUrl()));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowException() {
        when(repository.findById(KEY)).thenReturn(Optional.ofNullable(null));

        ShortUrlDTO urlDTO = service.find(KEY);
    }

    @Test
    public void createShortUrl() {
        ShortUrl shortUrl = ShortUrl.builder().url(URL).key(KEY).build();
        when(repository.findByUrl(URL)).thenReturn(Optional.ofNullable(null));
        when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(repository.save(any())).thenReturn(shortUrl);

        ShortUrlDTO shortUrlDTO = service.createShortUrl(URL);

        verify(repository).findByUrl(URL);
        verify(repository).findById(anyString());
        verify(repository).save(any());
        assertThat(shortUrlDTO.getKey(), equalTo(shortUrl.getKey()));
        assertThat(shortUrlDTO.getUrl(), equalTo(shortUrl.getUrl()));
    }

    @Test
    public void shouldReturnExistent() {
        ShortUrl shortUrl = ShortUrl.builder().url(URL).key(KEY).build();
        when(repository.findByUrl(URL)).thenReturn(Optional.ofNullable(shortUrl));

        ShortUrlDTO shortUrlDTO = service.createShortUrl(URL);

        verify(repository).findByUrl(URL);
        verify(repository, never()).findById(KEY);
        verify(repository, never()).save(any());
        assertThat(shortUrlDTO.getKey(), equalTo(shortUrl.getKey()));
        assertThat(shortUrlDTO.getUrl(), equalTo(shortUrl.getUrl()));
    }
}