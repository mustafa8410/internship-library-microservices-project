package com.example.webapi.repository;

import com.example.webapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>  {
     Long countAllByBookcaseId(Long bookcaseId);
     List<Book> findAllByBookcaseId(Long bookcaseId);
     List<Book> findAllByBookcaseIdIsNull();

     @Query("SELECT b FROM Book b LEFT JOIN Rented r ON b.id = r.book.id WHERE " +
             "(:title IS NULL OR b.title LIKE CONCAT('%', :title, '%')) AND " +
             "(:author IS NULL OR b.author LIKE CONCAT('%', :author, '%')) AND " +
             "(:bookcaseId IS NULL OR b.bookcase.id = :bookcaseId) AND " +
             "(:showOnlyUnrented = false OR r.book IS NULL)")
     List<Book> findByFilterByTitleAuthorBookcaseId(
             @Param("title") String title,
             @Param("author") String author,
             @Param("bookcaseId") Long bookcaseId,
             @Param("showOnlyUnrented") boolean showOnlyUnrented);


}
