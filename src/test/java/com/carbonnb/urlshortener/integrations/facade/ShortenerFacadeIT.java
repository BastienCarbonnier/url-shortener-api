package com.carbonnb.urlshortener.integrations.facade;

import com.carbonnb.urlshortener.exception.UrlShortenerTechnicalException;
import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.integrations.helper.PostgresqlContainerBase;
import com.carbonnb.urlshortener.model.dto.RequestDTO;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import com.carbonnb.urlshortener.model.dto.ResponseUrlDTO;
import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortenerFacadeIT extends PostgresqlContainerBase {
    @Autowired
    private ShortenerFacade shortenerFacade;

    @Test
    public void shouldShortUrlNotAlreadyExisting() {
        String fullUrl = "http://test.com/fullurlService";
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten(fullUrl);

        ResponseDTO<ShortenedUrlDTO> responseDTO = this.shortenerFacade.shortenUrl(requestDTO);

        Assertions.assertEquals(fullUrl, responseDTO.getData().getFullUrl());
        Assertions.assertFalse(responseDTO.getData().getAlreadyShortened());
    }

    @Test
    public void shouldReturnAlreadyShortenedUrl() {
        String fullUrl = "http://test.com/fullurlService2";
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten(fullUrl);

        ResponseDTO<ShortenedUrlDTO> responseDTO = this.shortenerFacade.shortenUrl(requestDTO);

        Assertions.assertEquals(fullUrl, responseDTO.getData().getFullUrl());
        Assertions.assertFalse(responseDTO.getData().getAlreadyShortened());

        ResponseDTO<ShortenedUrlDTO> responseDTOAlreadyShorten = this.shortenerFacade.shortenUrl(requestDTO);

        Assertions.assertEquals(fullUrl, responseDTOAlreadyShorten.getData().getFullUrl());
        Assertions.assertEquals(responseDTO.getData().getShortenedUrl(), responseDTOAlreadyShorten.getData().getShortenedUrl());
        Assertions.assertTrue(responseDTOAlreadyShorten.getData().getAlreadyShortened());
    }

    @Test
    public void shouldFindByShortenedUrl() throws UrlShortenerTechnicalException {
        String fullUrl = "http://test.com/fullurlService3";
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten(fullUrl);

        ResponseDTO<ShortenedUrlDTO> responseDTO = this.shortenerFacade.shortenUrl(requestDTO);
        ShortenedUrlDTO shortenedUrl = responseDTO.getData();
        Assertions.assertEquals(fullUrl, responseDTO.getData().getFullUrl());
        Assertions.assertFalse(responseDTO.getData().getAlreadyShortened());

        ResponseDTO<ResponseUrlDTO> responseUrlDTO = this.shortenerFacade.findByShortenedUrl(shortenedUrl.getShortenedUrl());

        Assertions.assertEquals(fullUrl, responseUrlDTO.getData().getFullUrl());
    }

    @Test
    public void shouldThrowWhenShortUrlDoesntExist() {
        Assertions.assertThrows(UrlShortenerTechnicalException.class, () -> this.shortenerFacade.findByShortenedUrl("notexist"));
    }
}
