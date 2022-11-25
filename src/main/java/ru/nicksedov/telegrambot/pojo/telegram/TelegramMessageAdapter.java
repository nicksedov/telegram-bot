package ru.nicksedov.telegrambot.pojo.telegram;

import java.util.function.Function;

public interface TelegramMessageAdapter<T> extends Function<T, TelegramMessage> {
}
