package com.example.batch.entity;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "renterId")
    private User renter;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    private Book book;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean alertSent;
}
