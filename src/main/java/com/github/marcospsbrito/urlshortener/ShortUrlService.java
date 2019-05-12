package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.mapper.ShortUrlMapper;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository repository;

    public ShortUrlDTO find(String key) {
        try {
            return ShortUrlMapper.toDTO(repository.findById(key).get());
        }catch (NoSuchElementException e){
            throw new NotFoundException("Key not registered");
        }
    }

    public ShortUrlDTO createShortUrl(String url) throws MalformedURLException {
        if(!new UrlValidator().isValid(url))
            throw new MalformedURLException();
        try {
            return ShortUrlMapper.toDTO(repository.findByUrl(url).get());
        }catch (NoSuchElementException e){
            ShortUrl shortUrl = create(url);
            return ShortUrlMapper.toDTO(repository.save(shortUrl));
        }
    }

    private ShortUrl create(String url) {
        String key;
        do{
            key = generateKey();
        }while(repository.findById(key).isPresent());
        return ShortUrl.builder().url(url).key(key).createAt(new Date()).build();
    }

    private String generateKey() {
        int length = (int) (Math.random()*31+5);
        return RandomStringUtils.random(length, true, true);
    }
}
