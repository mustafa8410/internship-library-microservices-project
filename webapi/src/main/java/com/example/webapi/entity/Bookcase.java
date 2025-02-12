package com.example.webapi.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "bookcase")
@Data
public class Bookcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long capacity;

}
