package com.example.ems.employee;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.department.DepartmentStatsDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stats")
public class EmployeeStatsController {

    private final EmployeeStatsService statsService;

    @RequestMapping("/employees/count")
    public ResponseEntity<Map<String, Long>> getEmployeeCount() {
        long count = statsService.countAll();
        return ResponseEntity.ok(Map.of("totalEmployees", count));
    }

    @GetMapping("/employees/by-department")
    public ResponseEntity<List<DepartmentStatsDto>> getStatsByDepartment() {
        return ResponseEntity.ok(statsService.countByDepartment());
    }
}
