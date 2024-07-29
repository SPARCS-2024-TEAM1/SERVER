package com.sparcs.team1.domain.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController implements TestApi {

    @Override
    @GetMapping("/test")
    public ResponseEntity<String> testHttps() {
        return ResponseEntity.ok("https 테스트");
    }
}
