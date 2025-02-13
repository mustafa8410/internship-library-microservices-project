package com.example.webapi.controller;

import com.example.webapi.entity.Bookcase;
import com.example.webapi.response.UpdateBookcaseResponse;
import com.example.webapi.service.BookcaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookcase")
public class BookcaseController {
    private final BookcaseService bookcaseService;

    public BookcaseController(BookcaseService bookcaseService) {
        this.bookcaseService = bookcaseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Bookcase> getBookcases(){
        return bookcaseService.getBookcases();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Bookcase findBookcaseById(@PathVariable Long id){
        return bookcaseService.findBookcaseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bookcase createBookcase(@RequestParam Optional<Long> capacity){
        return bookcaseService.createBookcase(capacity);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateBookcaseResponse> updateBookcaseById(@PathVariable Long id,
                                                                     @RequestParam Long newCapacity,
                                                                     @RequestParam Optional<String> redistribute){
        return bookcaseService.updateBookcaseById(id, newCapacity, redistribute);
    }

    @PutMapping("/distribute")
    public ResponseEntity<String> distributeBooksIntoBookcases(){
        return bookcaseService.distributeBooksIntoBookcases();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookcaseById(@PathVariable Long id,
                                                     @RequestParam Optional<String> redistributeBooks){
        return bookcaseService.deleteBookcaseById(id, redistributeBooks);
    }
}
