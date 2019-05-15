package com.github.marcospsbrito.urlshortener;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.marcospsbrito.urlshortener.exceptions.ExpiredException;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlCreateDTO;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UrlShortenerResource {

    @Autowired
    private ShortUrlService service;

    @JsonView(ShortUrlDTO.GetView.class)
    @RequestMapping(value = "/{key}",method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String key) {
        try{
            ShortUrlDTO shortUrlDTO = service.find(key);
            URI redirect = new URI(shortUrlDTO.getUrl());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirect);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExpiredException e) {
            return ResponseEntity.status(HttpStatus.GONE).body("This short url is expired");
        }
    }

    @JsonView(ShortUrlDTO.PostView.class)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ShortUrlDTO> post(@RequestBody ShortUrlCreateDTO dto){
        try {
            return ResponseEntity.ok(service.createShortUrl(dto));
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
}
