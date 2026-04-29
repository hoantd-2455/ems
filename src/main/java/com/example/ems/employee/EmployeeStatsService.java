package com.example.ems.employee;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.ems.department.DepartmentStatsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeStatsService {

    private final EmployeeRepository employeeRepository;

    @Cacheable("employeeCount")
    public long countAll() {
        long count = employeeRepository.count();
        log.debug("Total number of employees: {}", count);
        return count;
    }

    @Cacheable("departmentStats")
    public List<DepartmentStatsDto> countByDepartment() {
        log.debug("Loading department from db...");
        return employeeRepository.countEmployeesByDepartment();
    }

    @CacheEvict(value = { "employeeCount", "departmentStats" }, allEntries = true)
    public void evictEmployeeCountCache() {
        log.debug("Employee count cache evicted");
    }
}
