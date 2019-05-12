package com.github.marcospsbrito.urlshortener.model.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "short_url")
public class ShortUrl{
    @Id
    private String key;
    private String url;
    private Date createAt;

}

