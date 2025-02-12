package com.example.batch.response;

import com.example.batch.entity.User;
import lombok.Data;

@Data
public class UserResponse {
    String name;
    String surname;
    String mail;
    String username;
    String role;

    public UserResponse(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.mail = user.getMail();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
