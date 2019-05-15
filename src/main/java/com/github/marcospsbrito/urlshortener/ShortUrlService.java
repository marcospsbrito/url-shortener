package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.exceptions.ExpiredException;
import com.github.marcospsbrito.urlshortener.mapper.ShortUrlMapper;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlCreateDTO;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class ShortUrlService {

    private static final int EXPIRATION_TIME_DEFAULT = 5;

    @Autowired
    private ShortUrlRepository repository;

    @Autowired
    private KeyGenerator generator;

    public ShortUrlDTO find(String key) throws ExpiredException, NotFoundException {
        try {
            ShortUrl shortUrl = repository.findById(key).get();
            if(shortUrl.isActive())
                return ShortUrlMapper.toDTO(shortUrl);
            throw new ExpiredException();
        }catch (NoSuchElementException e){
            throw new NotFoundException("Key not registered");
        }
    }

    public ShortUrlDTO createShortUrl(ShortUrlCreateDTO dto) throws MalformedURLException {
        try {
            String validUrl = UrlUtils.validate(dto.getUrl());
            return ShortUrlMapper.toDTO(repository.findByUrlNotExpired(validUrl, Calendar.getInstance().getTime()).get());
        }catch (NoSuchElementException e){
            ShortUrl shortUrl = create(dto);
            return ShortUrlMapper.toDTO(repository.save(shortUrl));
        }
    }


    private ShortUrl create(ShortUrlCreateDTO url) throws MalformedURLException {
        String key;
        do{
            key = generator.generate();
        }while(repository.findById(key).isPresent());
        return ShortUrl.builder()
                .url(UrlUtils.validate(url.getUrl()))
                .key(key)
                .createAt(new Date())
                .expiresAt(getExpireDate(url.getExpiresInMinutes()))
                .build();
    }

    private Date getExpireDate(int expiresInMinutes) {
        if(expiresInMinutes <1)
            return DateUtils.addMinutes(new Date(), EXPIRATION_TIME_DEFAULT);
        else
            return DateUtils.addMinutes(new Date(), expiresInMinutes);
    }
}
