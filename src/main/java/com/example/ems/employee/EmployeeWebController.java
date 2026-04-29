package com.example.ems.employee;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ems.department.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final EmployeeStatsService statsService;

    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            Model model) {

        List<Employee> employees;
        // if name query param is present, search by name, else if department query
        // param is present, search by department, otherwise return all employees
        if (name != null && !name.isBlank()) {
            employees = employeeService.findByName(name);
        } else if (department != null && !department.isBlank()) {
            employees = employeeService.findByDepartmentName(department);
        } else {
            employees = employeeService.findAll();
        }

        model.addAttribute("employees", employees);
        model.addAttribute("nameQuery", name); // to pre-fill the search box with the current query
        model.addAttribute("deptQuery", department);

        log.debug("Listing employees with name: {} and department: {}", name, department);
        return "employees/list"; // Thymeleaf template at src/main/resources/templates/employees/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        // add an empty CreateEmployeeRequest object to the model to bind form data, and
        // also add the list of departments for the dropdown
        model.addAttribute("request", new CreateEmployeeRequest());

        // add the list of departments to the model for the dropdown
        model.addAttribute("departments", departmentService.findAll());
        return "employees/add";
    }

    @PostMapping("/add")
    public String addEmployee(
            @Valid @ModelAttribute("request") CreateEmployeeRequest request,
            BindingResult result, // BindingResult must come immediately after the @ModelAttribute parameter to
                                  // capture validation errors, it is requirement of Spring MVC
            Model model,
            RedirectAttributes redirectAttributes) {

        log.debug("Adding employee: {}", request.getName());

        // if there are validation errors, return to the form with error messages,
        // showing the form again with the list of departments
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            return "employees/add";
        }

        employeeService.create(request);

        // add a flash attribute to show a success message on the redirected page, this
        // message will be available on the redirected page and then removed from the
        // session after being accessed once
        redirectAttributes.addFlashAttribute("successMessage",
                "Employee " + request.getName() + " added successfully!");

        // after successful creation, redirect to the employee list page with a success
        // message
        log.info("Employee {} added successfully", request.getName());

        return "redirect:/employees/list";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("totalEmployees", statsService.countAll());
        model.addAttribute("deptStats", statsService.countByDepartment());
        log.debug("Loading statistics page");
        return "employees/statistics";
    }
}
