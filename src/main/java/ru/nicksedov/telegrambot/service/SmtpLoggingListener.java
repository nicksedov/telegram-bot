package ru.nicksedov.telegrambot.service;

import ch.astorm.smtp4j.SmtpServer;
import ch.astorm.smtp4j.core.SmtpMessage;
import ch.astorm.smtp4j.core.SmtpServerListener;
import jakarta.mail.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmtpLoggingListener implements SmtpServerListener {

    Logger logger = LoggerFactory.getLogger(SmtpLoggingListener.class);

    public void notifyStart(SmtpServer server) {
        logger.info("SMTP server has been started on port {}", server.getPort());
    }
    public void notifyClose(SmtpServer server) {
        logger.info("SMTP server has been closed");
    }
    public void notifyMessage(SmtpServer server, SmtpMessage message) {
        logger.info("Message has been received");
        logger.info("From: {}", message.getFrom());
        logger.info("To: {}", message.getRecipients(Message.RecipientType.TO));
        logger.info("Body: {}", message.getBody());
    }
}
