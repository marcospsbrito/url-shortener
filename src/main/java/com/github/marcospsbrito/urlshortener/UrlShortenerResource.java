package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;

@RestController
public class UrlShortenerResource {

    @Autowired
    private ShortUrlService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ShortUrlDTO> get(@RequestParam String key) {
        try{
            ShortUrlDTO shortUrlDTO = service.find(key);
            return ResponseEntity.ok(shortUrlDTO);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ShortUrlDTO> post(@RequestBody ShortUrlDTO dto){
        return ResponseEntity.ok(service.createShortUrl(dto.getUrl()));
    }
    
}
