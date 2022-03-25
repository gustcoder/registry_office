package br.com.docket.registryoffice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(path = "/health-check")
    public String healthCheck() {
        return "OK";
    }
}
