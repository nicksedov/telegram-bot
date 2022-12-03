package ru.nicksedov.telegrambot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nicksedov.telegrambot.component.MailToTelegramMessageAdapter;
import ru.nicksedov.telegrambot.component.TelegramService;
import ru.nicksedov.telegrambot.component.filter.LoggingFilter;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessage;

import java.io.IOException;

@RestController
public class APIController {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

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
    public ResponseEntity<Void> intercept(@RequestBody byte[] content) {
        String base64 = Base64Utils.encodeToString(content);
        logger.debug("Request body (base64 encoded):\n{}", base64);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@RequestBody MailContent content) throws IOException, InterruptedException {
        TelegramMessage telegramMessage = telegramMessageAdapter.apply(content);
        telegramService.postMessage(token, telegramMessage);
        return ResponseEntity.ok().build();
    }
}
