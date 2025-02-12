package com.example.webapi.request.auth;

import lombok.Data;

@Data
public class AuthRegisterRequest {
    String name;
    String surname;
    String mail;
    String username;
    String password;
}
