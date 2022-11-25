package ru.nicksedov.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.service.TelegramService;

import java.io.IOException;

@RestController
public class APIController {

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    private final TelegramService telegramService;

    public APIController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/intercept")
    public ResponseEntity<String> intercept(@RequestBody String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object jsonDeserialized = mapper.readValue(content, Object.class);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonDeserialized);
        logger.info("Accepted message:\n{}", json);
        return ResponseEntity.ok(json);
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@RequestBody MailContent content) throws IOException, InterruptedException {
        telegramService.postMessage(content);
        return ResponseEntity.ok().build();
    }
}
