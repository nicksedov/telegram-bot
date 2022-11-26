package ru.nicksedov.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessage;
import ru.nicksedov.telegrambot.component.MailToTelegramMessageAdapter;
import ru.nicksedov.telegrambot.component.TelegramService;

import java.io.IOException;

@RestController
public class APIController {

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

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
