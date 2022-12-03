package ru.nicksedov.telegrambot.component;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.function.UnaryOperator;

@Service
public class HtmlPreprocessor implements UnaryOperator<String> {

    private static final Logger logger = LoggerFactory.getLogger(HtmlPreprocessor.class);

    @Override
    public String apply(String text) {
        Document dom = Jsoup.parse(text);
        Charset charset = dom.charset();
        logger.debug("Accepted document charset: {}", charset);
        return text;
    }
}
