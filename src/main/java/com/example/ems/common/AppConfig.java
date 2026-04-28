package com.example.ems.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SimplePasswordEncoder simplePasswordEncoder() {
        return new SimplePasswordEncoder();
    }

    public static class SimplePasswordEncoder {
        public String encode(String rawPassword) {
            // Simple encoding logic (for demonstration purposes only)
            return "hashed:" + rawPassword;
        }

        public boolean matches(String rawPassword, String encodedPassword) {
            return encodedPassword.equals("hashed:" + rawPassword);
        }
    }
}
