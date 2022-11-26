package ru.nicksedov.telegrambot.pojo.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailContent {
    private String spf;
    private String id;
    private String date;
    private String subject;
    private String resent_date;
    private Body body;
    private Addresses addresses;
    @JsonProperty("embedded_files")
    private List<EmbeddedFile> embeddedFiles;
    private List<AttachmentFile> attachments;
}
