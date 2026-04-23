package com.example.ems.employee;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class EmployeeCodeGenerator {
    private final AtomicInteger counter = new AtomicInteger(0);

    public String generate() {
        String yearMonth = LocalDate.now().toString()
                .substring(0, 7)
                .replace("-", ""); // Get current year and month in format YYYYMM
        int sequence = counter.incrementAndGet(); // Increment the sequence number
        return String.format("EMP-%s-%03d", yearMonth, sequence); // Return employee code in format EMP-YYYYMM-SSS
    }

    public String formatName(String name) {
        if (name == null || name.isBlank())
            return "";
        String[] words = name.trim().split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return formattedName.toString().trim();
    }
}
