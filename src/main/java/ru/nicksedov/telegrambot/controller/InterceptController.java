package ru.nicksedov.telegrambot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.service.TelegramService;

import java.io.IOException;

@RestController
public class InterceptController {

    private final TelegramService telegramService;

    public InterceptController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/intercept")
    public ResponseEntity<Void> postIntercept(@RequestBody MailContent content) throws IOException, InterruptedException {
        telegramService.postMessage(content);
        return ResponseEntity.ok().build();
    }
}
