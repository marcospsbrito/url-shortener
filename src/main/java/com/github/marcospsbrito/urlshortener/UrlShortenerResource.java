package com.github.marcospsbrito.urlshortener;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.marcospsbrito.urlshortener.model.dto.ShortUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.net.MalformedURLException;

@RestController
public class UrlShortenerResource {

    @Autowired
    private ShortUrlService service;

    @JsonView(ShortUrlDTO.GetView.class)
    @RequestMapping(value = "/{key}",method = RequestMethod.GET)
    public ResponseEntity<ShortUrlDTO> get(@PathVariable String key) {
        try{
            ShortUrlDTO shortUrlDTO = service.find(key);
            return ResponseEntity.ok(shortUrlDTO);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @JsonView(ShortUrlDTO.PostView.class)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ShortUrlDTO> post(@RequestBody ShortUrlDTO dto){
        return ResponseEntity.ok(service.createShortUrl(dto.getUrl()));
    }
    
}
