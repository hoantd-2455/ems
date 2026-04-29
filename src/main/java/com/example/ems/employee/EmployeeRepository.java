package com.example.ems.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ems.department.DepartmentStatsDto;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName")
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

    // Custom query to count employees by department
    // Use LEFT JOIN to include departments with zero employees
    @Query("SELECT new com.example.ems.department.DepartmentStatsDto(d.name, COUNT(e)) " +
            "FROM Department d " +
            "LEFT JOIN d.employees e " +
            "GROUP BY d.id, d.name " +
            "ORDER BY COUNT(e) DESC")
    List<DepartmentStatsDto> countEmployeesByDepartment();

    @Query("SELECT new com.example.ems.department.DepartmentStatsDto(d.name, COUNT(e)) " +
            "FROM Department d " +
            "LEFT JOIN d.employees e " +
            "GROUP BY d.id, d.name " +
            "ORDER BY COUNT(e) DESC " +
            "LIMIT 1")
    Optional<DepartmentStatsDto> findTopDepartmentByEmployeeCount();
}
