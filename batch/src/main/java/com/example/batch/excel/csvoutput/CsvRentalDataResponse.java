package com.example.batch.excel.csvoutput;

import com.example.batch.entity.Book;
import com.example.batch.entity.Rented;
import com.example.batch.entity.User;
import com.example.batch.excel.CsvFileMapping;
import com.example.batch.excel.FieldName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CsvRentalDataResponse {
    @CsvFileMapping(value = FieldName.USERNAME)
    String renterUsername;
    @CsvFileMapping(value = FieldName.USER_ID)
    Long renterId;
    @CsvFileMapping(value = FieldName.MAIL)
    String renterMail;
    @CsvFileMapping(value = FieldName.TITLE)
    String bookTitle;
    @CsvFileMapping(value = FieldName.BOOK_ID)
    Long bookId;
    @CsvFileMapping(value = FieldName.BOOKCASE_ID)
    Long bookcaseId;
    @CsvFileMapping(value = FieldName.START_DATE)
    Date startDate;
    @CsvFileMapping(value = FieldName.END_DATE)
    Date endDate;
    @CsvFileMapping(value = FieldName.ALERT_SENT)
    boolean alertSent;

    public CsvRentalDataResponse(User user, Book book, Rented rented) {
        this.renterUsername = user.getUsername();
        this.renterId = user.getId();
        this.renterMail = user.getMail();
        this.bookTitle = book.getTitle();
        this.bookId = book.getId();
        this.startDate = rented.getStartDate();
        this.endDate = rented.getEndDate();
        this.alertSent = rented.isAlertSent();
        if(book.getBookcase() != null)
            this.bookcaseId = book.getBookcase().getId();
    }

    public CsvRentalDataResponse(Rented rented) {
        this.renterUsername = rented.getRenter().getUsername();
        this.renterId = rented.getRenter().getId();
        this.renterMail = rented.getRenter().getMail();
        this.bookTitle = rented.getBook().getTitle();
        this.bookId = rented.getBook().getId();
        this.startDate = rented.getStartDate();
        this.endDate = rented.getEndDate();
        this.alertSent = rented.isAlertSent();
        if(rented.getBook().getBookcase() != null)
            this.bookcaseId = rented.getBook().getBookcase().getId();
    }

    public String[] getFieldsArray(){
        String [] fields = {
                this.renterUsername, String.valueOf(this.renterId), this.renterMail, this.bookTitle,
                String.valueOf(this.bookId), String.valueOf(bookcaseId), this.startDate.toString(),
                this.endDate.toString(), String.valueOf(this.alertSent)
        };
        return fields;
    }
}
