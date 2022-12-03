package ru.nicksedov.telegrambot.component.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(1)
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        logger.info("Got HTTP request: {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        boolean isPost = HttpMethod.POST.matches(httpServletRequest.getMethod());
        if (isPost && logger.isDebugEnabled()) {
            // Wrap the request so the controller can read the request content too
            HttpServletRequestWrapper wrapper = new ContentCachingRequestWrapper(httpServletRequest);
            // Get the input stream from the wrapper and convert it into byte array
            byte[] body = StreamUtils.copyToByteArray(wrapper.getInputStream());
            // use jackson ObjectMapper to convert the byte array to Map (represents JSON)
            if (body.length > 0) {
                ObjectMapper mapper = new ObjectMapper();
                Map<?, ?> jsonDeserialized = mapper.readValue(body, Map.class);
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonDeserialized);
                logger.debug("Request content:\n{}", json);
            }
        }
        chain.doFilter(request, response);
    }
}
