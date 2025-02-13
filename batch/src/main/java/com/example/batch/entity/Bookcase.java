package com.example.batch.entity;

import com.example.batch.excel.csvinput.BookcaseExcel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bookcase")
@Data
@NoArgsConstructor
public class Bookcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long capacity;

    public Bookcase(BookcaseExcel bookcaseExcel) {
        this.capacity = bookcaseExcel.getCapacity();
    }

}
