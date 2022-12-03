package ru.nicksedov.telegrambot.component;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;

@Service
public class TextPreprocessor implements UnaryOperator<String> {

    @Override
    public String apply(String s) {
        byte[] bytes;
        try {
            bytes = s.getBytes("koi8-r");
        } catch (UnsupportedEncodingException e) {
            bytes = ("Unsupported encoding koi8-r").getBytes(StandardCharsets.UTF_8);
        }
        return new String(bytes);
    }
}
