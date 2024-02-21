package com.carbonnb.urlshortener.unit.controller;

import com.carbonnb.urlshortener.controller.ShortenerController;
import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.model.dto.RequestDTO;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShortenerController.class)
public class ShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortenerFacade shortenerFacade;


    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SHORT_URL = "shorturl";
    private static final String FULL_URL = "http://www.google.com/test";

    @Test
    public void shouldShortUrl() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten(FULL_URL);

        ResponseDTO<ShortenedUrlDTO> responseDTO = new ResponseDTO<>();
        ShortenedUrlDTO shortenedUrlDTO = new ShortenedUrlDTO();
        shortenedUrlDTO.setFullUrl(FULL_URL);
        shortenedUrlDTO.setShortenedUrl(SHORT_URL);
        shortenedUrlDTO.setAlreadyShortened(false);
        responseDTO.setData(shortenedUrlDTO);


        when(shortenerFacade.shortenUrl(requestDTO)).thenReturn(responseDTO);
        mockMvc.perform(post("/url-shortener/shorten")
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}