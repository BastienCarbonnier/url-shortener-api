package com.carbonnb.urlshortener.controller;

import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.model.RequestDTO;
import com.carbonnb.urlshortener.model.ResponseDTO;
import com.carbonnb.urlshortener.model.ShortenUrlDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/url-shortener")
public class ShortenerController {
     private ShortenerFacade shortenerFacade;

     public ShortenerController(ShortenerFacade shortenerFacade) {
          this.shortenerFacade = shortenerFacade;
     }

     @PostMapping("/shorten")
     public ResponseEntity<ResponseDTO<ShortenUrlDTO>> shortenUrl(@RequestBody RequestDTO request) {
          return new ResponseEntity<>(this.shortenerFacade.shortenUrl(request), HttpStatus.OK);
     }
}
