package com.example.ems.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.common.AppConfig;

@RestController
public class HelloController {

    private final AppConfig.SimplePasswordEncoder passwordEncoder;

    public HelloController(AppConfig.SimplePasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/demo/encode")
    public String demoEncode(@RequestParam String password) {
        return "Password hash: " + passwordEncoder.encode(password);
    }
}
