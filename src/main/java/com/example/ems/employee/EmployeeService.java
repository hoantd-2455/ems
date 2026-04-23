package com.example.ems.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    // In-memory list to store employees (for demonstration purposes)
    private final List<Employee> employees = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    private final EmployeeCodeGenerator codeGenerator;

    public EmployeeService(EmployeeCodeGenerator generator) {
        this.codeGenerator = generator;

        // Mock data
        employees.add(new Employee(idCounter.getAndIncrement(), codeGenerator.generate(), "Trang", "trang@example.com",
                "HR"));
        employees.add(new Employee(idCounter.getAndIncrement(), codeGenerator.generate(), "Phong", "phong@example.com",
                "IT"));
        employees.add(
                new Employee(idCounter.getAndIncrement(), codeGenerator.generate(), "Hoa", "hoa@example.com", "QA"));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Optional<Employee> findById(Long id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public Employee create(Employee employee) {
        employee.setId(idCounter.getAndIncrement());
        employee.setEmployeeCode(codeGenerator.generate());
        employee.setName(codeGenerator.formatName(employee.getName()));
        employees.add(employee);
        return employee;
    }

    public boolean deleteById(Long id) {
        return employees.removeIf(e -> e.getId().equals(id));
    }
}
