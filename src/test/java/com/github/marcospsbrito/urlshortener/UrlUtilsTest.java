package com.github.marcospsbrito.urlshortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UrlUtilsTest {

    private static final String VALID_URL = "github.com";
    private static final String INVALID_URL = "%%github.com%%";
    private static final String HTTP_URL = "http://github.com";
    private static final String HTTPS_URL = "https://github.com";

    @Test
    public void validateWithoutHttp() throws MalformedURLException {
        String url = UrlUtils.validate(VALID_URL);

        assertThat(url).endsWith(VALID_URL);
    }

    @Test
    public void validatehttpUrl() throws MalformedURLException {
        String url = UrlUtils.validate(HTTP_URL);

        assertThat(url).isEqualTo(HTTP_URL);
    }

    @Test
    public void validateHttpsUrl() throws MalformedURLException {
        String url = UrlUtils.validate(HTTPS_URL);

        assertThat(url).isEqualTo(HTTPS_URL);
    }

    @Test(expected = MalformedURLException.class)
    public void throwsMalformedURLException() throws MalformedURLException {
        UrlUtils.validate(INVALID_URL);
    }

}