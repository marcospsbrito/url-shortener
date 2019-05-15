package com.github.marcospsbrito.urlshortener;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

public class UrlUtils {

    public static String validate(String url) throws MalformedURLException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        if (!UrlValidator.getInstance().isValid(url))
            throw new MalformedURLException("The informed URL ["+url+"] is not a valid URL.");
        return url;
    }
}