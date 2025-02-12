package com.example.webapi.response;

import com.example.webapi.entity.Bookcase;
import lombok.Data;

@Data
public class UpdateBookcaseResponse {
    Bookcase bookcase;
    String message;

    public UpdateBookcaseResponse(Bookcase bookcase, String message) {
        this.bookcase = bookcase;
        this.message = message;
    }
}
