package com.example.batch.response;

import com.example.batch.entity.Book;
import lombok.Data;

import java.util.Date;

@Data
public class BookResponse {
    Long id;
    String title;
    String description;
    String author;
    Date registrationDate;
    Long bookcaseId;

    public BookResponse(Book book){
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.author = book.getAuthor();
        this.registrationDate = book.getRegistrationDate();
        if(book.getBookcase() != null)
            this.bookcaseId = book.getBookcase().getId();
    }
}
