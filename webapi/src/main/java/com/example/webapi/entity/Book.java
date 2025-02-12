package com.example.webapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "book")
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
    @JsonIgnore
    private Bookcase bookcase;
}

