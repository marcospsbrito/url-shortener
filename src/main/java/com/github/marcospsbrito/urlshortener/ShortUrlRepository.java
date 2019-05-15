package com.github.marcospsbrito.urlshortener;

import com.github.marcospsbrito.urlshortener.model.entity.ShortUrl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortUrl, String>{

    @Query(value = "select url from ShortUrl url where url.url = :url and url.expiresAt >= :currentDate")
    Optional<ShortUrl> findByUrlNotExpired(String url, Date currentDate);
}
