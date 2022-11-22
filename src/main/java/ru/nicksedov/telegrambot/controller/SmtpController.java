package ru.nicksedov.telegrambot.controller;

import ch.astorm.smtp4j.SmtpServer;
import ch.astorm.smtp4j.SmtpServerBuilder;
import ch.astorm.smtp4j.core.DefaultSmtpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nicksedov.telegrambot.pojo.StatusReport;
import ru.nicksedov.telegrambot.service.SmtpLoggingListener;

import java.io.IOException;

@RestController
@RequestMapping("/smtp")
public class SmtpController {

    private static Logger logger = LoggerFactory.getLogger(SmtpController.class);

    private SmtpLoggingListener loggingListener;

    private SmtpServer server;

    public SmtpController(SmtpLoggingListener loggingListener) {
        this.loggingListener = loggingListener;
    }

    @GetMapping("/start")
    public ResponseEntity<StatusReport> start(@RequestParam(defaultValue = "25") int port) {
        SmtpServerBuilder builder = new SmtpServerBuilder();
        if (server != null && server.isRunning()) {
            return ResponseEntity.ok(new StatusReport("SKIP", "Server instance already running on port " + port));
        }
        try (SmtpServer server = builder.withPort(port).build()) {
            server.addListener(loggingListener);
            DefaultSmtpMessageHandler store = new DefaultSmtpMessageHandler();
            server.addListener(store);
            server.start();
            this.server = server;
            return ResponseEntity.ok(new StatusReport("OK", "Listening on port " + port));
        } catch (IOException e) {
            logger.error("Failed to start SMTP server", e);
            return ResponseEntity.internalServerError().body(new StatusReport("FAIL", e.getMessage()));
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<StatusReport> stop() {
        if (server != null && server.isRunning()) {
            try {
                int port = server.getPort();
                server.close();
                return ResponseEntity.ok(new StatusReport("OK", "Stopped listening on port " + port));
            } catch (IOException e) {
                logger.error("Failed to stop SMTP server", e);
                return ResponseEntity.internalServerError().body(new StatusReport("FAIL", e.getMessage()));
            }
        } else {
            return ResponseEntity.ok(new StatusReport("SKIP", "No server is running now"));
        }
    }
}
