package com.newlens.backend.controller;

import com.newlens.backend.dto.HealthResponse;
import java.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public HealthResponse health() {
        return new HealthResponse("ok", "newlens-backend", Instant.now());
    }
}
