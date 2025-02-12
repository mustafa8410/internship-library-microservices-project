package com.example.batch.repository;

import com.example.batch.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    Long countAllByBookcaseId(Long bookcaseId);
}
