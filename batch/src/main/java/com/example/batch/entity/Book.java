package com.example.batch.entity;

import com.example.batch.excel.BookExcel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "book")
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String author;

    @Temporal(value = TemporalType.DATE)
    private Date registrationDate;

    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "renterId")
    //@JsonIgnore
    //private User renter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookcaseId")
    private Bookcase bookcase;

    public Book(BookExcel bookExcel) {
        this.title = bookExcel.getTitle();
        this.description = bookExcel.getDescription();
        this.author = bookExcel.getAuthor();
    }
}

