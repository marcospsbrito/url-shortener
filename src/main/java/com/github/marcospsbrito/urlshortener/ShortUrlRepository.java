package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortUrl, String>{

    Optional<ShortUrl> findByUrl(String url);
}
