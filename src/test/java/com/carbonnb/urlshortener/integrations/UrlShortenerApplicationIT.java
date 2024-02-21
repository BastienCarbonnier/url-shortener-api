package com.carbonnb.urlshortener.integrations;

import com.carbonnb.urlshortener.integrations.helper.PostgresqlContainerBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerApplicationIT extends PostgresqlContainerBase {

    @Test
    void contextLoads() {
    }

}
