package ru.nicksedov.telegrambot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class APIControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void postIntercept() {
        String url = "http://localhost:" + port + "/intercept";
        String body = "{\"id\": 0, \"nested\": {\"first\": \"value1\", \"second\": \"value2\"}}";
        String responseBody = restTemplate.postForObject(url, body, String.class);
    }
}