package com.example.ems.employee;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.ems.department.Department;
import com.example.ems.department.DepartmentRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeCodeGenerator codeGenerator;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmployeeCodeGenerator codeGenerator) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.codeGenerator = codeGenerator;
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
            return true;
        }
        return false;
    }

    public Employee create(CreateEmployeeRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        String employeeCode = codeGenerator.generate();

        Employee employee = new Employee(null, employeeCode, codeGenerator.formatName(request.getName()),
                request.getEmail(), department);
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        if (request.getName() != null) {
            employee.setName(codeGenerator.formatName(request.getName()));
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
            employee.setDepartment(department);
        }
        return employeeRepository.save(employee);
    }

}
