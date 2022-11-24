package ru.nicksedov.telegrambot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.Duration;

@RestController
public class AboutController {

    @Value("classpath:templates/about.html")
    private Resource aboutHtml;

    private static long startTimestamp = System.currentTimeMillis();

    @GetMapping("/about")
    public ResponseEntity<String> about() {
        String bodyTemplate;
        try (InputStream is = aboutHtml.getInputStream()) {
            bodyTemplate = StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to create response");
        }
        long timestamp = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(timestamp - startTimestamp);
        String body = bodyTemplate.replace("${uptime}", duration.toString());
        return ResponseEntity.ok(body);
    }
}
