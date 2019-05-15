package com.github.marcospsbrito.urlshortener;

import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Singleton;

@Singleton
public class KeyGenerator {

    public String generate() {
        int length = (int) (Math.random()*31+5);
        return RandomStringUtils.random(length, true, true);
    }

}
