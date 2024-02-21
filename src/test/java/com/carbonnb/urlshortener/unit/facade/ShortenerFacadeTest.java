package com.carbonnb.urlshortener.unit.facade;

import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.integrations.repository.ShortenedUrlRepository;
import com.carbonnb.urlshortener.integrations.services.ShortenedUrlService;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import com.carbonnb.urlshortener.model.dto.RequestDTO;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
import com.carbonnb.urlshortener.utils.UrlShortenerUtils;
import org.junit.jupiter.api.*;
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
@SpringBootTest(classes = ShortenerFacade.class)
public class ShortenerFacadeTest {

    @MockBean
    private ShortenedUrlService shortenedUrlService;

    @MockBean
    private ShortenedUrlRepository shortenedUrlRepository;

    @Autowired
    private ShortenerFacade shortenerFacade;

    MockedStatic<UrlShortenerUtils> mockedStatic = Mockito.mockStatic(UrlShortenerUtils.class);

    private static final String SHORT_URL = "shorturl";
    private static final String FULL_URL = "http://www.google.com/test";

    private RequestDTO requestDTO;
    private ResponseDTO<ShortenedUrlDTO> responseDTOAlreadyPresent;

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
        this.requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten(FULL_URL);

        this.responseDTOAlreadyPresent = new ResponseDTO<>();
        ShortenedUrlDTO shortenedUrlDTO = new ShortenedUrlDTO();
        shortenedUrlDTO.setFullUrl(FULL_URL);
        shortenedUrlDTO.setShortenedUrl(SHORT_URL);
        shortenedUrlDTO.setAlreadyShortened(true);

        responseDTOAlreadyPresent.setData(shortenedUrlDTO);

        this.shortenedUrl = new ShortenedUrl();
        shortenedUrl.setFullUrl(FULL_URL);
        shortenedUrl.setShortenedUrl(SHORT_URL);
    }
    @Test
    public void shouldReturnShortUrlIfPresent() {
        when(this.shortenedUrlService.findByFullUrl(FULL_URL)).thenReturn(Optional.of(this.shortenedUrl));
        ResponseDTO<ShortenedUrlDTO> result = this.shortenerFacade.shortenUrl(requestDTO);
        Assertions.assertEquals(responseDTOAlreadyPresent, result);
    }

    @Test
    public void shouldCreateAndReturnShortUrlIfNotPresent() {
        when(this.shortenedUrlService.findByFullUrl(FULL_URL)).thenReturn(Optional.empty());

        ResponseDTO<ShortenedUrlDTO> result = this.shortenerFacade.shortenUrl(requestDTO);
        ShortenedUrlDTO data = result.getData();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getFullUrl(), requestDTO.getUrlToShorten());
        Assertions.assertEquals(data.getShortenedUrl(), SHORT_URL);
        Assertions.assertFalse(data.getAlreadyShortened());
    }
}