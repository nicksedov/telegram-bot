package ru.nicksedov.telegrambot.pojo.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TelegramMessage {

    @JsonProperty("chat_id")
    private int chatId;

    @JsonProperty
    private String text;
}
