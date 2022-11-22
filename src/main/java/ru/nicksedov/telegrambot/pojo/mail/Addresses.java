package ru.nicksedov.telegrambot.pojo.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Addresses {
    private From from;
    @JsonProperty("to")
    private To myto;
}
