package com.example.webapi.request.user;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    String name;
    String surname;
    String mail;
    String username;
    String password;
    String role;

    public CreateUserRequest(String name, String surname, String mail, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public CreateUserRequest(){}
}
