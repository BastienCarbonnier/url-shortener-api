package com.carbonnb.urlshortener.integrations.controller;

import com.carbonnb.urlshortener.exception.ErrorCodeExceptionEnum;
import com.carbonnb.urlshortener.facade.ShortenerFacade;
import com.carbonnb.urlshortener.integrations.helper.PostgresqlContainerBase;
import com.carbonnb.urlshortener.model.ErrorMessage;
import com.carbonnb.urlshortener.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShortenerControllerIT extends PostgresqlContainerBase {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortenerFacade shortenerFacade;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String FULL_URL = "http://www.google.com/test";

    @Test
    public void shouldShortUrl() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten("http://www.google.com/test1");

        ResultActions resultActions = mockMvc.perform(post("/url-shortener/shorten")
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        ResponseShortenedUrlDTO response = objectMapper.readValue(contentAsString, ResponseShortenedUrlDTO.class);
        ShortenedUrlDTO data = response.getData();
        Assertions.assertFalse(data.getAlreadyShortened());
    }

    @Test
    public void shouldReturnAlreadyShortenUrl() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrlToShorten("http://www.google.com/test2");

        MvcResult result = mockMvc.perform(post("/url-shortener/shorten")
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        ResponseShortenedUrlDTO response = objectMapper.readValue(contentAsString, ResponseShortenedUrlDTO.class);
        ShortenedUrlDTO data = response.getData();
        Assertions.assertFalse(data.getAlreadyShortened());

        MvcResult resultAlready = mockMvc.perform(post("/url-shortener/shorten")
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsStringAlready = resultAlready.getResponse().getContentAsString();
        ResponseShortenedUrlDTO responseAlready = objectMapper.readValue(contentAsStringAlready, ResponseShortenedUrlDTO.class);
        ShortenedUrlDTO dataAlready = responseAlready.getData();

        Assertions.assertTrue(dataAlready.getAlreadyShortened());
        Assertions.assertEquals(data.getShortenedUrl(), dataAlready.getShortenedUrl());
    }

    @Test
    public void shouldReturnShortenedUrl() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        String fullUrl = "http://www.google.com/test-return-shortened-url";
        requestDTO.setUrlToShorten(fullUrl);

        MvcResult resultActionsShorten = mockMvc.perform(post("/url-shortener/shorten")
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = resultActionsShorten.getResponse().getContentAsString();
        ResponseShortenedUrlDTO response = objectMapper.readValue(contentAsString, ResponseShortenedUrlDTO.class);
        ShortenedUrlDTO data = response.getData();

        MvcResult resultGetFullUrl = mockMvc.perform(get("/url-shortener/")
                        .param("shortUrlId", data.getShortenedUrl())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsStringGet = resultGetFullUrl.getResponse().getContentAsString();
        ResponseUrlShortenerDTO responseGet = objectMapper.readValue(contentAsStringGet, ResponseUrlShortenerDTO.class);
        ResponseUrlDTO dataGet = responseGet.getData();

        Assertions.assertEquals(fullUrl, dataGet.getFullUrl());
    }

    @Test
    public void shouldReturnErrorIfShortenedUrlDoesntExist() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        String fullUrl = "http://www.google.com/not-existing-url";
        requestDTO.setUrlToShorten(fullUrl);

        MvcResult resultActionsShorten = mockMvc.perform(get("/url-shortener/")
                        .param("shortUrlId", "notexist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        String contentAsString = resultActionsShorten.getResponse().getContentAsString();
        ResponseErrorDTO response = objectMapper.readValue(contentAsString, ResponseErrorDTO.class);
        ErrorMessage errorMessage = response.getErrors();

        Assertions.assertEquals(ErrorCodeExceptionEnum.SHORT_URL_DOESNT_EXIST.getHttpStatus().value(), errorMessage.getStatusCode());
        Assertions.assertEquals(ErrorCodeExceptionEnum.SHORT_URL_DOESNT_EXIST.getUserInfo(), errorMessage.getMessage());
    }
}
