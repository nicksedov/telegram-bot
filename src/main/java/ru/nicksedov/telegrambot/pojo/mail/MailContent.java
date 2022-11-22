package ru.nicksedov.telegrambot.pojo.mail;

import lombok.Getter;
import lombok.Setter;

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
}
