package ru.nicksedov.telegrambot.pojo.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentFile {
    private String filename;
    @JsonProperty("content_type")
    private String contentType;
    private String data;
}
