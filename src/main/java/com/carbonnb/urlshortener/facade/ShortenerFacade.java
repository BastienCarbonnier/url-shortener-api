package com.carbonnb.urlshortener.facade;

import com.carbonnb.urlshortener.exception.ErrorCodeExceptionEnum;
import com.carbonnb.urlshortener.exception.UrlShortenerTechnicalException;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import com.carbonnb.urlshortener.model.dto.*;
import com.carbonnb.urlshortener.integrations.services.ShortenedUrlService;
import com.carbonnb.urlshortener.utils.UrlShortenerUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class ShortenerFacade {

    private final ShortenedUrlService shortenedUrlService;
    public ShortenerFacade(ShortenedUrlService shortenedUrlService) {
        this.shortenedUrlService = shortenedUrlService;
    }

    /**
     * Method to shorten an Url, it will save the full url in a postgresql database and return the shorten one
     * If Url is already present in database it will return the shortened that already exist
     * Otherwise it will create and save the shorten one
     * @param request Request containing full url to shorten
     * @return shortened url and a boolean to know if it was already shortened
     */
    public ResponseDTO<ShortenedUrlDTO> shortenUrl(RequestDTO request) {
        ResponseDTO<ShortenedUrlDTO> response = new ResponseDTO<>();
        ShortenedUrlDTO shortenedUrlDTO = new ShortenedUrlDTO();

        // We check if full url was already shortened
        Optional<ShortenedUrl> shortenedUrlOptional = this.shortenedUrlService.findByFullUrl(request.getUrlToShorten());

        if (shortenedUrlOptional.isPresent()) { // Was already shortened
            ShortenedUrl existingShortenedUrl = shortenedUrlOptional.get();
            shortenedUrlDTO.setAlreadyShortened(true);
            shortenedUrlDTO.setShortenedUrl(existingShortenedUrl.getShortenedUrl());
            String decodedFullUrl = UrlShortenerUtils.decodeFromBase64Url(existingShortenedUrl.getFullUrl());
            shortenedUrlDTO.setFullUrl(decodedFullUrl);
        } else {
            ShortenedUrl shortenUrl = new ShortenedUrl();

            // We encode Url to base 64 (TODO: url should be encrypt to avoid personal information to be visible in db)
            String encodedFullUrl = UrlShortenerUtils.encodeToBase64Url(request.getUrlToShorten());
            shortenUrl.setFullUrl(encodedFullUrl);

            String shortUrl = UrlShortenerUtils.shortenUrl(shortenUrl.getId().toString());
            shortenUrl.setShortenedUrl(shortUrl);

            // We save the new shortened Url
            this.shortenedUrlService.save(shortenUrl);

            shortenedUrlDTO.setShortenedUrl(shortenUrl.getShortenedUrl());
            shortenedUrlDTO.setFullUrl(UrlShortenerUtils.decodeFromBase64Url(shortenUrl.getFullUrl()));
        }

        // We return the new response DTO
        response.setData(shortenedUrlDTO);

        return response;
    }

    /**
     * Method to get the full Url from a shortened one
     * @param shortUrlId Short Url id corresponding to a full Url in db
     * @return Full url, if not present in database return a 404 Exception
     * @throws UrlShortenerTechnicalException Technical Exception thrown if short url doesn't exist in database
     */
    public ResponseDTO<ResponseUrlDTO> findByShortenedUrl(String shortUrlId) throws UrlShortenerTechnicalException {
        ResponseDTO<ResponseUrlDTO> response = new ResponseDTO<>();

        ResponseUrlDTO responseUrlDTO = new ResponseUrlDTO();

        ShortenedUrl shortenedUrl = this.shortenedUrlService.findByShortenedUrl(shortUrlId).orElseThrow(
                () -> new UrlShortenerTechnicalException(ErrorCodeExceptionEnum.SHORT_URL_DOESNT_EXIST)
        );

        responseUrlDTO.setFullUrl(UrlShortenerUtils.decodeFromBase64Url(shortenedUrl.getFullUrl()));
        response.setData(responseUrlDTO);
        return response;
    }
}
