package ru.nicksedov.telegrambot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nicksedov.telegrambot.pojo.TextMessage;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class TelegramService {
    private static final int CHAT_ID = -893187249;
    private static final String TOKEN = "5730532194:AAFPluN4ENc64MuiftC076WKcQmUmMH9iBA";

    private HttpClient httpClient;

    private ObjectMapper jsonMapper;

    public TelegramService(HttpClient httpClient, ObjectMapper jsonMapper) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
    }

    public void postMessage(MailContent mailContent) throws IOException, InterruptedException {
        String text = mailContent.getBody().getText();
        if (text != null) {
            int pos = text.indexOf("[DISCLAIMER]");
            if (pos > 0) {
                text = text.substring(0, pos);
            }
        }
        String message =
                "=== Subject ===\n" +
                mailContent.getSubject() +
                "\n=== Message ===\n" + text;

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

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
