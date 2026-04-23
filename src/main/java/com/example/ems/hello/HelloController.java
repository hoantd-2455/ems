package com.example.ems.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.common.AppConfig;
import com.example.ems.employee.EmployeeCodeGenerator;

@RestController
public class HelloController {

    private final EmployeeCodeGenerator codeGenerator;
    private final AppConfig.SimplePasswordEncoder passwordEncoder;

    public HelloController(EmployeeCodeGenerator codeGenerator, AppConfig.SimplePasswordEncoder passwordEncoder) {
        this.codeGenerator = codeGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/demo/code")
    public String generateEmployeeCode() {
        String code = codeGenerator.generate();
        return "Generated Employee Code: " + code;
    }

    @GetMapping("/demo/employee")
    public String demoEmployee(@RequestParam String name, @RequestParam String password) {
        String code = codeGenerator.generate();
        String formattedName = codeGenerator.formatName(name);
        String encodedPassword = passwordEncoder.encode(password);
        return String.format("Code: %s | Name: %s | Password hash: %s", code, formattedName, encodedPassword);
    }
}
