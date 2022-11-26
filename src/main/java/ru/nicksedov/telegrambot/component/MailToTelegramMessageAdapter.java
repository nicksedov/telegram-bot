package ru.nicksedov.telegrambot.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nicksedov.telegrambot.pojo.mail.Body;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessage;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessageAdapter;

import java.util.Optional;

@Service
public class MailToTelegramMessageAdapter implements TelegramMessageAdapter<MailContent> {

    @Value("${telegram.chatId}")
    private int chatId;

    @Override
    public TelegramMessage apply(MailContent mailContent) {
        Body body = Optional.ofNullable(mailContent.getBody()).orElseThrow();
        String text = Optional.ofNullable(body.getText()).orElse(body.getHtml());
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
        return new TelegramMessage(chatId, message);
    }
}
