package com.apnasahomestay.auth_service.controller;

import com.apnasahomestay.auth_service.dto.LoginRequest;
import com.apnasahomestay.auth_service.dto.LoginResponse;
import com.apnasahomestay.auth_service.dto.RegisterRequest;
import com.apnasahomestay.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/test")
    public String test(){
        return "Hi 200 OK Success";
    }
}