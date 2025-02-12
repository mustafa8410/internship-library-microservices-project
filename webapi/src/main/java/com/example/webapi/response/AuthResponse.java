package com.example.webapi.response;

import lombok.Data;

@Data
public class AuthResponse {
    Long userId;
    String jwtToken;

    public AuthResponse(Long userId, String jwtToken) {
        this.userId = userId;
        this.jwtToken = jwtToken;
    }
}
