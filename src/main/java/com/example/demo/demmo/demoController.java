package com.example.demo.demmo;

import com.example.demo.auth.authenticationRequest;
import com.example.demo.auth.registerRequest;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class demoController {
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?>sayHello(){
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?>PostTest(@AuthenticationPrincipal User user, @RequestBody authenticationRequest request){

        return ResponseEntity.ok(user);
    }
}
