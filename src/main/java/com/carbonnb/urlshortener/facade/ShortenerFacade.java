package com.carbonnb.urlshortener.facade;

import com.carbonnb.urlshortener.exception.ErrorCodeExceptionEnum;
import com.carbonnb.urlshortener.exception.UrlShortenerTechnicalException;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import com.carbonnb.urlshortener.model.dto.*;
import com.carbonnb.urlshortener.services.ShortenedUrlService;
import com.carbonnb.urlshortener.utils.UrlShortenerUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class ShortenerFacade {

    private final ShortenedUrlService shortenedUrlService;
    public ShortenerFacade(ShortenedUrlService shortenedUrlService) {
        this.shortenedUrlService = shortenedUrlService;
    }

    public ResponseDTO<ShortenedUrlDTO> shortenUrl(RequestDTO request) {
        ResponseDTO<ShortenedUrlDTO> response = new ResponseDTO<>();
        ShortenedUrlDTO shortenedUrlDTO = new ShortenedUrlDTO();

        Optional<ShortenedUrl> shortenedUrlOptional = this.shortenedUrlService.findByFullUrl(request.getUrlToShorten());
        if (shortenedUrlOptional.isPresent()) {
            ShortenedUrl existingShortenedUrl = shortenedUrlOptional.get();
            shortenedUrlDTO.setAlreadyShortened(true);
            shortenedUrlDTO.setShortenedUrl(existingShortenedUrl.getShortenedUrl());
            String decodedFullUrl = UrlShortenerUtils.decodeUrl(existingShortenedUrl.getFullUrl());
            shortenedUrlDTO.setFullUrl(decodedFullUrl);
        } else {
            ShortenedUrl shortenUrl = new ShortenedUrl();

            String encodedFullUrl = UrlShortenerUtils.encodeUrl(request.getUrlToShorten());
            shortenUrl.setFullUrl(encodedFullUrl);

            // TODO: Generate Int64 de 0 to 15M encoded to base62 wiil be the
            shortenUrl.setShortenedUrl(UUID.randomUUID().toString());
            this.shortenedUrlService.save(shortenUrl);

            shortenedUrlDTO.setShortenedUrl(shortenUrl.getShortenedUrl());
            shortenedUrlDTO.setFullUrl(shortenUrl.getFullUrl());
        }

        response.setData(shortenedUrlDTO);

        return response;
    }

    public ResponseDTO<ResponseUrlDTO> findByShortenedUrl(String shortUrl) throws UrlShortenerTechnicalException {
        ResponseDTO<ResponseUrlDTO> response = new ResponseDTO<>();

        // TODO: Handle error if not exist return 404
        ResponseUrlDTO responseUrlDTO = new ResponseUrlDTO();

        ShortenedUrl shortenedUrl = this.shortenedUrlService.findByShortenedUrl(shortUrl).orElseThrow(
                () -> new UrlShortenerTechnicalException(ErrorCodeExceptionEnum.SHORT_URL_DOESNT_EXIST)
        );

        responseUrlDTO.setFullUrl(UrlShortenerUtils.decodeUrl(shortenedUrl.getFullUrl()));
        response.setData(responseUrlDTO);
        return response;
    }
}
