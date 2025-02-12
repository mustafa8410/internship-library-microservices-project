package com.example.webapi.request.book;

import lombok.Getter;

@Getter
public class CreateBookRequest {
    String title;
    String description;
    String author;

}
