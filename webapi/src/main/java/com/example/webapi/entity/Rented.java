package com.example.webapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "rented")
public class Rented {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renterId")
    @JsonIgnore
    private User renter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    @JsonIgnore
    private Book book;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean alertSent;
}
