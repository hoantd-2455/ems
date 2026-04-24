package com.example.ems.employee;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ems.department.Department;
import com.example.ems.department.DepartmentRepository;
import com.example.ems.exception.ResourceNotFoundException;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Employee> findByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName);
    }

    public boolean deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);

            log.warn("Employee deleted: id={}", id);
            return true;
        }

        log.warn("Attempted to delete non-existent employee: id={}", id);
        return false;
    }

    public Employee create(CreateEmployeeRequest request) {
        // concat string in log message to avoid unnecessary string concatenation when
        // debug logging is disabled
        log.debug("Creating employee with email: {}", request.getEmail());
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with ID: " + request.getDepartmentId()));

        Employee employee = new Employee(null, request.getName(), request.getEmail(), department);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created with ID: {} , Name: {}, Email: {}, Department: {}", savedEmployee.getId(),
                savedEmployee.getName(), savedEmployee.getEmail(), savedEmployee.getDepartment().getName());
        return savedEmployee;
    }

    public Employee update(Long id, UpdateEmployeeRequest request) {
        log.debug("Updating employee id={}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        if (request.getName() != null) {
            employee.setName(request.getName());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + request.getDepartmentId()));
            employee.setDepartment(department);
        }
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated with ID: {} , Name: {}, Email: {}, Department: {}", updatedEmployee.getId(),
                updatedEmployee.getName(), updatedEmployee.getEmail(), updatedEmployee.getDepartment().getName());
        return updatedEmployee;
    }

}
