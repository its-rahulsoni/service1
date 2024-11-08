package com.systemdesign.tutorials.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api1")
public class TestController {

    // A simple GET request
    @GetMapping("/hello")
    public String sayHello() {
        return "Service 01 says - Hello, World!";
    }

}
