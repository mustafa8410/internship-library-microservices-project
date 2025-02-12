package com.example.webapi.request.auth;

import lombok.Getter;

@Getter
public class AuthLoginRequest {
    String username;
    String password;
}
