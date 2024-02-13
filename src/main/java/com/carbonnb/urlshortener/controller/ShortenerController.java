package com.carbonnb.urlshortener.controller;

import com.carbonnb.urlshortener.exception.UrlShortenerTechnicalException;
import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.model.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/url-shortener")
public class ShortenerController {
     private final ShortenerFacade shortenerFacade;

     public ShortenerController(ShortenerFacade shortenerFacade) {
          this.shortenerFacade = shortenerFacade;
     }

     /**
      * Endpoint to short an Url and persist it in database
      * @param request The full url to short
      * @return The shortened Url
      */
     @PostMapping("/shorten")
     public ResponseEntity<ResponseDTO<ShortenedUrlDTO>> shortenUrl(@RequestBody RequestDTO request) {
          return new ResponseEntity<>(this.shortenerFacade.shortenUrl(request), HttpStatus.OK);
     }

     /**
      * Endpoint to get full url from shortened one
      * @param shortUrlId Shortened Url id
      * @return The full url if present otherwise throw a 404 exception
      * @throws UrlShortenerTechnicalException Technical exception thrown if short url doesn't exist in database
      */
     @GetMapping("/")
     public ResponseEntity<ResponseDTO<ResponseUrlDTO>> getFullUrlForShortenedOne(@RequestParam(required = false) String shortUrlId) throws UrlShortenerTechnicalException {
          return new ResponseEntity<>(this.shortenerFacade.findByShortenedUrl(shortUrlId), HttpStatus.OK);
     }
}
