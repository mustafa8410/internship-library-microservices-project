package com.example.webapi.request.user;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    Long userId;
    String newName;
    String newSurname;
    String newMail;
    String newUsername;
    String newPassword;
}
