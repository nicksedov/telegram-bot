package ru.nicksedov.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nicksedov.telegrambot.pojo.TextMessage;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@RestController
public class NotifyController {

    private static final int CHAT_ID = -893187249;
    private static final String TOKEN = "5730532194:AAFPluN4ENc64MuiftC076WKcQmUmMH9iBA";

    private final HttpClient httpClient;

    private final ObjectMapper jsonMapper;

    public NotifyController(HttpClient httpClient, ObjectMapper jsonMapper) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping("/send")
    public ResponseEntity<String> postMessage() throws IOException, InterruptedException {
        String message = "Hello World from Java 11";

        UriBuilder builder = UriComponentsBuilder
                .fromUriString("https://api.telegram.org")
                .path("/{token}/sendMessage");

        TextMessage content = new TextMessage(CHAT_ID, message);
        String body = jsonMapper.writeValueAsString(content);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(builder.build("bot" + TOKEN))
                .header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());

        return ResponseEntity.status(response.statusCode()).body(response.body());
    }

}
