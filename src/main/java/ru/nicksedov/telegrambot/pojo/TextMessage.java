package ru.nicksedov.telegrambot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TextMessage {

    @JsonProperty("chat_id")
    private int chatId;

    @JsonProperty
    private String text;
}
