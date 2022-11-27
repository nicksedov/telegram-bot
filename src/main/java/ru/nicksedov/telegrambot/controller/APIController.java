package ru.nicksedov.telegrambot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nicksedov.telegrambot.component.MailToTelegramMessageAdapter;
import ru.nicksedov.telegrambot.component.TelegramService;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessage;

import java.io.IOException;

@RestController
public class APIController {

    private final TelegramService telegramService;

    private final MailToTelegramMessageAdapter telegramMessageAdapter;

    @Value("${telegram.token}")
    private String token;

    public APIController(
            TelegramService telegramService,
            MailToTelegramMessageAdapter telegramMessageAdapter) {
        this.telegramService = telegramService;
        this.telegramMessageAdapter = telegramMessageAdapter;
    }

    @PostMapping("/intercept")
    public ResponseEntity<Void> intercept() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@RequestBody MailContent content) throws IOException, InterruptedException {
        TelegramMessage telegramMessage = telegramMessageAdapter.apply(content);
        telegramService.postMessage(token, telegramMessage);
        return ResponseEntity.ok().build();
    }
}
