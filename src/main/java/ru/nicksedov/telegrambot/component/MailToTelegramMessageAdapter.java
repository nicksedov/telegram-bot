package ru.nicksedov.telegrambot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.nicksedov.telegrambot.pojo.mail.Body;
import ru.nicksedov.telegrambot.pojo.mail.MailContent;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessage;
import ru.nicksedov.telegrambot.pojo.telegram.TelegramMessageAdapter;

import java.util.Optional;

@Service
public class MailToTelegramMessageAdapter implements TelegramMessageAdapter<MailContent> {

    private static final Logger logger = LoggerFactory.getLogger(TelegramMessageAdapter.class);

    @Autowired
    private HtmlPreprocessor htmlPreprocessor;

    @Autowired
    private TextPreprocessor textPreprocessor;

    @Value("${telegram.chatId}")
    private int chatId;

    @Override
    public TelegramMessage apply(MailContent mailContent) {
        Body body = Optional.ofNullable(mailContent.getBody()).orElseThrow();
        String text;
        if (StringUtils.hasText(body.getHtml())) {
            text = body.getHtml();
            logger.debug(htmlPreprocessor.apply(text));
        } else if (StringUtils.hasText(body.getText())) {
            text = body.getText();
            logger.debug(textPreprocessor.apply(text));
        } else {
            text = "";
        }
        String message =
                "=== Subject ===\n" +
                        mailContent.getSubject() +
                        "\n=== Message ===\n" + text;
        return new TelegramMessage(chatId, message);
    }
}
