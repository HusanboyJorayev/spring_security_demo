package com
.example.spring_security_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("custom")
public class CustomController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "Only user use";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Only admin use";
    }

    @GetMapping("/manager")
    public String manager() {
        return "Only manager use";
    }

    @GetMapping("/permitAll")
    public String permitAll() {
        return "everyone uses";
    }
}
