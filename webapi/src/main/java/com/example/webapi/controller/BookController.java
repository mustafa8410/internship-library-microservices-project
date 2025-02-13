package com.example.webapi.controller;

import com.example.webapi.request.book.CreateBookRequest;
import com.example.webapi.request.book.UpdateBookRequest;
import com.example.webapi.response.BookResponse;
import com.example.webapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public BookResponse findBookById(@PathVariable Long id){
        return bookService.findBookById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookResponse> findBooks(@RequestParam Optional<String> title, @RequestParam Optional<String> author,
                                        @RequestParam Optional<Long> bookcaseId,
                                        @RequestParam Optional<String> showRented){
        return bookService.findBooks(title, author, bookcaseId, showRented);
    }

    @GetMapping("/all")
    public List<BookResponse> getBooks(){
        return bookService.getBooks();
    }



    @GetMapping("/count")
    public Long findCountOfBooks(@RequestParam Optional<Long> bookcaseId){
        return bookService.findCountOfBooks(bookcaseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@RequestBody CreateBookRequest createBookRequest,
                                   @RequestParam (defaultValue = "true") String place,
                                   @RequestParam Optional<Long> bookcaseId){
        return bookService.createBook(createBookRequest, place, bookcaseId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(@RequestBody UpdateBookRequest updateBookRequest){
        return bookService.updateBook(updateBookRequest);
    }

    @PutMapping("/place/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse placeBookIntoBookcaseById(@PathVariable Long id, @RequestParam Optional<Long> bookcaseId){
        return bookService.placeBookIntoBookcaseById(id, bookcaseId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }

}
