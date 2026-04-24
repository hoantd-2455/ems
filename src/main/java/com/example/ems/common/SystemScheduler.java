package com.example.ems.common;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemScheduler {

    private static final Logger log = LoggerFactory.getLogger(SystemScheduler.class);

    @Scheduled(fixedRate = 30000)
    public void systemHealthCheck() {
        log.info("System running - {} ", LocalDateTime.now());
    }

}
