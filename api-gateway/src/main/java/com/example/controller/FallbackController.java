package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/users")
    public Map<String, String> usersFallback() {
        return Map.of(
                "message", "User Service временно недоступен. Попробуйте позже.",
                "status", "SERVICE_UNAVAILABLE"
        );
    }
}