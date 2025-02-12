package com.example.webapi.repository;

import com.example.webapi.entity.Book;
import com.example.webapi.entity.Rented;
import com.example.webapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentedRepository extends JpaRepository<Rented, Long> {
    Optional<Rented> findByBookId(Long bookId);
    boolean existsByBook(Book book);
    List<Rented> findAllByRenter(User renter);
}
