package com.carbonnb.urlshortener.unit.services;

import com.carbonnb.urlshortener.integrations.repository.ShortenedUrlRepository;
import com.carbonnb.urlshortener.integrations.services.ShortenedUrlService;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import com.carbonnb.urlshortener.utils.UrlShortenerUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShortenedUrlService.class)
public class ShortenedUrlServiceTest {

    @MockBean
    private ShortenedUrlRepository shortenedUrlRepository;

    @Autowired
    private ShortenedUrlService shortenedUrlService;

    MockedStatic<UrlShortenerUtils> mockedStatic = Mockito.mockStatic(UrlShortenerUtils.class);

    private static final String SHORT_URL = "shorturl";
    private static final String FULL_URL = "http://www.google.com/test";

    private ShortenedUrl shortenedUrl;

    @BeforeEach
    public void setupMock() {
        this.mockedStatic.when(() -> UrlShortenerUtils.shortenUrl(anyString())).thenReturn(SHORT_URL);
        this.mockedStatic.when(() -> UrlShortenerUtils.decodeFromBase64Url(anyString())).thenReturn(FULL_URL);
        this.mockedStatic.when(() -> UrlShortenerUtils.encodeToBase64Url(anyString())).thenReturn(FULL_URL);
    }

    @AfterEach
    public void cleanMock() {
        this.mockedStatic.close();
    }

    @BeforeEach
    public void setup() {
        this.shortenedUrl = new ShortenedUrl();
        shortenedUrl.setFullUrl(FULL_URL);
        shortenedUrl.setShortenedUrl(SHORT_URL);
    }

    @Test
    public void shouldReturnShortenedUrlByFullUrl() {
        when(this.shortenedUrlRepository.findByFullUrl(FULL_URL)).thenReturn(Optional.of(this.shortenedUrl));
        Assertions.assertTrue(this.shortenedUrlService.findByFullUrl(FULL_URL).isPresent());
    }

    @Test
    public void shouldReturnShortenedUrlByShortUrl() {
        when(this.shortenedUrlRepository.findByShortenedUrl(SHORT_URL)).thenReturn(Optional.of(this.shortenedUrl));
        Assertions.assertTrue(this.shortenedUrlService.findByShortenedUrl(SHORT_URL).isPresent());
    }

    @Test
    public void shouldNotReturnAnythingIfFullUrlDoesntExist() {
        when(this.shortenedUrlRepository.findByFullUrl(anyString())).thenReturn(Optional.empty());
        Assertions.assertTrue(this.shortenedUrlService.findByFullUrl(FULL_URL).isEmpty());
    }

    @Test
    public void shouldNotReturnAnythingIfShortUrlDoesntExist() {
        when(this.shortenedUrlRepository.findByShortenedUrl(anyString())).thenReturn(Optional.empty());
        Assertions.assertTrue(this.shortenedUrlService.findByShortenedUrl(SHORT_URL).isEmpty());
    }
}