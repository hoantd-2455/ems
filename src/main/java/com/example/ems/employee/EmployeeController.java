package com.example.ems.employee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.exception.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchByName(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department) {
        if (name != null) {
            return ResponseEntity.ok(employeeService.findByName(name));
        }
        if (department != null) {
            return ResponseEntity.ok(employeeService.findByDepartmentName(department));
        }
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody CreateEmployeeRequest request) {
        Employee created = employeeService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest request) {
        Employee updated = employeeService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (employeeService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
