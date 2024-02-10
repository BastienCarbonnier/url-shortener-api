package com.carbonnb.urlshortener.facade;

import com.carbonnb.urlshortener.model.dto.RequestDTO;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
import com.carbonnb.urlshortener.services.ShortenedUrlService;
import org.springframework.stereotype.Component;


@Component
public class ShortenerFacade {

    private final ShortenedUrlService shortenedUrlService;
    public ShortenerFacade(ShortenedUrlService shortenedUrlService) {
        this.shortenedUrlService = shortenedUrlService;
    }

    public ResponseDTO<ShortenedUrlDTO> shortenUrl(RequestDTO request) {
        ShortenedUrlDTO shortenUrl = new ShortenedUrlDTO();
        shortenUrl.setOriginalUrl(request.getUrlToShorten());
        shortenUrl.setShortenedUrl(request.getResourceHost());

        ResponseDTO<ShortenedUrlDTO> response = new ResponseDTO<>();
        response.setData(shortenUrl);

        this.shortenedUrlService.save(shortenUrl);




        return response;
    }
}
