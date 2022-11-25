package ru.nicksedov.telegrambot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessage;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class TelegramService {
    private final HttpClient httpClient;

    private final ObjectMapper jsonMapper;

    public TelegramService(HttpClient httpClient, ObjectMapper jsonMapper) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
    }

    public void postMessage(@NonNull String token, @NonNull TelegramMessage content)
            throws IOException, InterruptedException {

        UriBuilder builder = UriComponentsBuilder
                .fromUriString("https://api.telegram.org")
                .path("/{token}/sendMessage");

        String body = jsonMapper.writeValueAsString(content);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(builder.build("bot" + token))
                .header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .timeout(Duration.ofSeconds(5))
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
