package com.example.demo.demmo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class demoController {
    @PostAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<String>sayHello(){
        return ResponseEntity.ok("hello");
    }
}
