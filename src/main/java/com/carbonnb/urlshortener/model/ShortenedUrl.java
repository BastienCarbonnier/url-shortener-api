package com.carbonnb.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "shortened-url")
public class ShortenedUrl {

    public ShortenedUrl() {
        this.id = UUID.randomUUID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_url")
    String fullUrl;

    @Column(name = "shortened_url")
    String shortenedUrl;
}