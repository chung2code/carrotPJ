package com.example.demo.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping
    public String  healthCheck(){
        return "healthCheck ok";
    }
}
