package com.alanjsantos.productapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok().build();
    }
}
