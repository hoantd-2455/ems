package com.example.ems.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeStatsService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeStatsService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeStatsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable("employeeCount")
    public long countAll() {
        long count = employeeRepository.count();
        log.debug("Total number of employees: {}", count);
        return count;
    }

    @CacheEvict(value = "employeeCount", allEntries = true)
    public void evictEmployeeCountCache() {
        log.debug("Employee count cache evicted");
    }
}
