package com.saswat.autopay.serviceImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationShutdownListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Application is shutting down at {}", LocalDateTime.now());
    }
}