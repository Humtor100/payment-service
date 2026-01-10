package com.kirito.payment_service.controller;

import com.kirito.payment_service.dto.RegisterDTO;
import com.kirito.payment_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO request) { // Создай RegisterDTO (email, password)
        authService.register(request.getEmail(), request.getPassword());
        return "User registered successfully";
    }
}
