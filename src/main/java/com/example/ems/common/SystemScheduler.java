package com.example.ems.common;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SystemScheduler {
    @Scheduled(fixedRate = 30000)
    public void systemHealthCheck() {
        log.info("System running - {} ", LocalDateTime.now());
    }
}
