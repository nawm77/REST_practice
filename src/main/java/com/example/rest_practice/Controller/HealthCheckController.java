package com.example.rest_practice.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {
    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        log.info("PONG");
        return ResponseEntity.ok("pong");
    }
}
