package com.example.webapi.request.book;

import lombok.Getter;

@Getter
public class UpdateBookRequest {
    Long bookId;
    String newTitle;
    String newDesc;
    String newAuthor;
    Long newBookcaseId;
}
