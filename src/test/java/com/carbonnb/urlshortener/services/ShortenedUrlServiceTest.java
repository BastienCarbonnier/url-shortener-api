package com.carbonnb.urlshortener.services;

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
public class ShortenedUrlServiceTest extends PostgresqlContainerBaseTest {
    @Autowired
    private ShortenedUrlService shortenedUrlService;

    @Test
    public void saveShortenedUrl() {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setShortenedUrl("servShort1");
        shortenedUrl.setFullUrl("http://test.com/fullurlserv1");

        this.shortenedUrlService.save(shortenedUrl);
    }

    @Test
    public void saveShortenedUrlShouldThrow() {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setShortenedUrl("toolongshorturl");
        shortenedUrl.setFullUrl("http://test.com/fullurl");

        Assertions.assertThrows(Exception.class, () -> this.shortenedUrlService.save(shortenedUrl));
    }

    @Test
    public void shouldFindByFullUrl() {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        String fullUrl = "http://test.com/fullurlService";
        shortenedUrl.setShortenedUrl("servShort2");
        shortenedUrl.setFullUrl(fullUrl);

        this.shortenedUrlService.save(shortenedUrl);

        Optional<ShortenedUrl> foundShortenUrlOptional = this.shortenedUrlService.findByFullUrl(fullUrl);
        // Assertions.assertTrue(foundShortenUrlOptional.isPresent());

        //ShortenedUrl foundShortenUrl = foundShortenUrlOptional.get();
        // Assertions.assertEquals(foundShortenUrl, shortenedUrl);
    }
}
