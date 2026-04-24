package com.example.ems.employee;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class EmployeeStatsController {

    private final EmployeeStatsService statsService;

    public EmployeeStatsController(EmployeeStatsService statsService) {
        this.statsService = statsService;
    }

    @RequestMapping("/employees/count")
    public ResponseEntity<Map<String, Long>> getEmployeeCount() {
        long count = statsService.countAll();
        return ResponseEntity.ok(Map.of("totalEmployees", count));
    }
}
