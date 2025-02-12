package com.example.webapi.request.rent;

import lombok.Getter;

@Getter
public class CreateRentRequest {
    Long bookId;
    Long userId;
    Long numberOfDays;
}
