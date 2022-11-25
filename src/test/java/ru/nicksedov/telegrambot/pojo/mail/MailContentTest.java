package ru.nicksedov.telegrambot.pojo.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@SpringBootTest
class MailContentTest {

    @Value("classpath:incoming/mail.json")
    private Resource mailJson;

    @Test
    void deserialize() throws IOException {
        try (InputStream is = mailJson.getInputStream()) {
            String jsonContent = StreamUtils.copyToString(is, Charset.defaultCharset());
            ObjectMapper mapper = new ObjectMapper();
            MailContent mailContent = mapper.readValue(jsonContent, MailContent.class);
            Assert.notNull(mailContent, "mailContent is null");
        }
    }
}