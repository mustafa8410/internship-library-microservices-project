package com.example.batch.entity;

import com.example.batch.excel.csvinput.UserExcel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public User(UserExcel userExcel) {
        this.name = userExcel.getName();
        if(userExcel.getSurname() != null && !userExcel.getSurname().isEmpty())
            this.surname = userExcel.getSurname();
        this.mail = userExcel.getMail();
        this.username = userExcel.getUsername();
        this.role = userExcel.getRole();
    }
}
