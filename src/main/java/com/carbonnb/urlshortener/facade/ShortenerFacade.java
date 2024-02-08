package com.carbonnb.urlshortener.facade;

import com.carbonnb.urlshortener.model.RequestDTO;
import com.carbonnb.urlshortener.model.ResponseDTO;
import com.carbonnb.urlshortener.model.ShortenUrlDTO;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;

@Component
public class ShortenerFacade {
    public ShortenerFacade() {

    }

    public ResponseDTO<ShortenUrlDTO> shortenUrl(RequestDTO request) {
        ShortenUrlDTO shortenUrl = new ShortenUrlDTO();
        shortenUrl.setOriginalUrl(request.getUrlToShorten());
        shortenUrl.setShortenUrl(request.getResourceHost());

        ResponseDTO<ShortenUrlDTO> response = new ResponseDTO<>();
        response.setData(shortenUrl);
        return response;
    }
}
