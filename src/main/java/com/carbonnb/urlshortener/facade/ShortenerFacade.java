package com.carbonnb.urlshortener.facade;

import com.carbonnb.urlshortener.model.ShortenedUrl;
import com.carbonnb.urlshortener.model.dto.RequestDTO;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
import com.carbonnb.urlshortener.services.ShortenedUrlService;
import com.carbonnb.urlshortener.utils.UrlShortenerUtils;
import org.springframework.stereotype.Component;


@Component
public class ShortenerFacade {

    private final ShortenedUrlService shortenedUrlService;
    public ShortenerFacade(ShortenedUrlService shortenedUrlService) {
        this.shortenedUrlService = shortenedUrlService;
    }

    public ResponseDTO<ShortenedUrlDTO> shortenUrl(RequestDTO request) {
        ShortenedUrl shortenUrl = new ShortenedUrl();
        shortenUrl.setOriginalUrl(request.getUrlToShorten());

        String encodedUrl = UrlShortenerUtils.encodeUrl(request.getUrlToShorten());

        shortenUrl.setShortenedUrl(encodedUrl);
        String existingUrl = this.shortenedUrlService.getShortenedUrl(encodedUrl);

        this.shortenedUrlService.save(shortenUrl);

        ResponseDTO<ShortenedUrlDTO> response = new ResponseDTO<>();
        ShortenedUrlDTO shortenedUrlDTO = new ShortenedUrlDTO(shortenUrl);
        // Generate Int64 de 0 to 15M encoded to base62 wiil be the
        shortenedUrlDTO.setAlreadyShortened(true);
        response.setData(shortenedUrlDTO);

        return response;
    }
}
