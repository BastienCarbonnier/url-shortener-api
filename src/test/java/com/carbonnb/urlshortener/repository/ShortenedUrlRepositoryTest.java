package com.carbonnb.urlshortener.repository;

import com.carbonnb.urlshortener.PostgresqlContainerBaseTest;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortenedUrlRepositoryTest extends PostgresqlContainerBaseTest {
    @Autowired
    private ShortenedUrlRepository shortenedUrlRepository;

    @Test
    public void saveShortenedUrl() {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setShortenedUrl("short1");
        shortenedUrl.setFullUrl("http://test.com/fullurl");

        this.shortenedUrlRepository.save(shortenedUrl);

        Assertions.assertTrue(this.shortenedUrlRepository.existsById(shortenedUrl.getId()));
    }

    @Test
    public void saveShortenedUrlShouldThrow() {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setShortenedUrl("toolongshorturl");
        shortenedUrl.setFullUrl("http://test.com/fullurl2");

        Assertions.assertThrows(Exception.class, () -> this.shortenedUrlRepository.save(shortenedUrl));
    }

    @Test
    public void shouldFindByFullUrl() {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        String fullUrl = "http://test.com/fullurl3";
        shortenedUrl.setShortenedUrl("short2");
        shortenedUrl.setFullUrl(fullUrl);
        this.shortenedUrlRepository.save(shortenedUrl);
        Optional<ShortenedUrl> foundShortenUrlOptional = this.shortenedUrlRepository.findByFullUrl(fullUrl);
        Assertions.assertTrue(foundShortenUrlOptional.isPresent());
        ShortenedUrl foundShortenUrl = foundShortenUrlOptional.get();
        Assertions.assertEquals(foundShortenUrl.getShortenedUrl(), shortenedUrl.getShortenedUrl());
        Assertions.assertEquals(foundShortenUrl.getFullUrl(), shortenedUrl.getFullUrl());

        Assertions.assertEquals(foundShortenUrl.getId(), shortenedUrl.getId());
        Assertions.assertEquals(foundShortenUrl, shortenedUrl);


    }
}
