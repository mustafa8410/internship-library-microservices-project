package com.example.webapi.controller;

import com.example.webapi.request.auth.AuthLoginRequest;
import com.example.webapi.request.auth.AuthRegisterRequest;
import com.example.webapi.response.AuthResponse;
import com.example.webapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthLoginRequest authLoginRequest){
        return authService.login(authLoginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody AuthRegisterRequest authRegisterRequest){
        return authService.register(authRegisterRequest);
    }
}
