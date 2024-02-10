package com.carbonnb.urlshortener.controller;

import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.model.dto.RequestDTO;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
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

     @PostMapping("/shorten")
     public ResponseEntity<ResponseDTO<ShortenedUrlDTO>> shortenUrl(@RequestBody RequestDTO request) {
          return new ResponseEntity<>(this.shortenerFacade.shortenUrl(request), HttpStatus.OK);
     }
}
