package ru.nicksedov.telegrambot.component;

import net.htmlparser.jericho.Source;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;

@Service
public class HtmlPreprocessor implements UnaryOperator<String> {

    @Override
    public String apply(String text) {
        Source html = new Source(text);
        String encoding = html.getEncoding();
        byte[] bytes;
        try {
            bytes = html.toString().getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            bytes = ("Unsupported encoding " + encoding).getBytes(StandardCharsets.UTF_8);
        }
        return new String(bytes);
    }
}
