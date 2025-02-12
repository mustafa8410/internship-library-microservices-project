package com.example.webapi.service;

import com.example.webapi.entity.Book;
import com.example.webapi.entity.Bookcase;
import com.example.webapi.exceptions.bookcase.AllBookcasesFullException;
import com.example.webapi.exceptions.bookcase.BookcaseNotFoundException;
import com.example.webapi.exceptions.bookcase.NoExistingBookcaseException;
import com.example.webapi.exceptions.global.InvalidParameterException;
import com.example.webapi.repository.BookRepository;
import com.example.webapi.repository.BookcaseRepository;
import com.example.webapi.response.UpdateBookcaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class BookcaseService {

    private final BookcaseRepository bookcaseRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    public BookcaseService(BookcaseRepository bookcaseRepository, BookRepository bookRepository, BookService bookService) {
        this.bookcaseRepository = bookcaseRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }


    public List<Bookcase> getBookcases(){
        List<Bookcase> bookcaseList = bookcaseRepository.findAll();
        if(bookcaseList.size() > 0)
            return bookcaseList;
        else
            throw new NoExistingBookcaseException();
    }

    public Bookcase findBookcaseById(Long bookcaseId){
        Bookcase bookcase = bookcaseRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        return bookcase;
    }

    public Bookcase createBookcase(Optional<Long> capacity){
        Bookcase bookcase = new Bookcase();
        if(capacity.isPresent())
            bookcase.setCapacity(capacity.get());
        else
            bookcase.setCapacity(20L);
        return bookcaseRepository.save(bookcase);
    }

    public ResponseEntity<UpdateBookcaseResponse> updateBookcaseById(Long bookcaseId, Long newCapacity, Optional<String> redistribute){
        Bookcase bookcase = bookcaseRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        bookcase.setCapacity(newCapacity);
        List<Book> bookList = bookRepository.findAllByBookcaseId(bookcaseId);
        if(bookList.size() > newCapacity){
            Book current;
            if(!redistribute.isPresent() || redistribute.get().equals("false")){
                ListIterator iterator = bookList.listIterator((newCapacity.intValue()));
                while(iterator.hasNext()) {
                    current = ((Book) iterator.next());
                    current.setBookcase(null);
                    bookRepository.save(current);
                }
            }
            else if(redistribute.get().equals("true")){
                List<Book> booksToRedistribute = bookList.subList(newCapacity.intValue(), bookList.size());
                List<Bookcase> bookcaseList = bookcaseRepository.findAllByIdIsNotOrderByCapacityDesc(bookcaseId);
                ResponseEntity<String> responseEntity = distributeBooksIntoBookcases(booksToRedistribute, bookcaseList);
                return new ResponseEntity<>(new UpdateBookcaseResponse(bookcase, responseEntity.getBody()), responseEntity.getStatusCode());
            }
            else
                throw new InvalidParameterException();
        }
        return new ResponseEntity<>(new UpdateBookcaseResponse(bookcaseRepository.save(bookcase), "Updated successfully."), HttpStatus.OK);
    }

    public void distributeBooks(List<Book> bookList, List<Bookcase> bookcaseList){
        int bookcaseIndex = 0;
        boolean placed = true;
        for(Book book: bookList){
            placed = false;
            while(!placed && bookcaseIndex < bookcaseList.size()){
                try{
                    bookService.placeBookIntoBookcaseById(book.getId(), Optional.of(bookcaseList.get(bookcaseIndex).getId()));
                    placed = true;
                }
                catch (Exception e){
                    bookcaseIndex++;
                }
            }
            if(!placed){
                throw new AllBookcasesFullException("There was not enough space for all books, some are left undistributed.");
            }
        }
    }

    public ResponseEntity<String> distributeBooksIntoBookcases(){
        List<Book> bookList = bookRepository.findAllByBookcaseIdIsNull();
        List<Bookcase> bookcaseList = bookcaseRepository.findAllByOrderByCapacityDesc();
        return distributeBooksIntoBookcases(bookList, bookcaseList);
    }

    public ResponseEntity<String> distributeBooksIntoBookcases(List<Book> bookList, List<Bookcase> bookcaseList){
        String message = "Distributed successfully.";
        try {
            distributeBooks(bookList, bookcaseList);
        }
        catch (Exception e){
            message = "There was not enough space for all books, some are left undistributed.";
            return new ResponseEntity<>(message, HttpStatus.INSUFFICIENT_STORAGE);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



    public ResponseEntity<String> deleteBookcaseById(Long bookcaseId, Optional<String> redistributeBooks){
        if(redistributeBooks.isPresent() && (!redistributeBooks.get().equals("true") && !redistributeBooks.get().equals("false")))
            throw new InvalidParameterException();
        Bookcase bookcase = bookcaseRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        List<Book> bookList = bookRepository.findAllByBookcaseId(bookcaseId);
        List<Bookcase> bookcaseList = bookcaseRepository.findAllByIdIsNotOrderByCapacityDesc(bookcaseId);
        // now we need to replace those books into the bookcases.
        String message = "";
        if(!redistributeBooks.isPresent() || (redistributeBooks.get().equals("true"))){
            try {
                distributeBooks(bookList, bookcaseList);
            }
            catch (Exception e) {
                message = "There was not enough space for all books, some are left undistributed.";
                bookcaseRepository.delete(bookcase);
                return new ResponseEntity<>(message, HttpStatus.INSUFFICIENT_STORAGE);
            }
        }
        bookcaseRepository.delete(bookcase);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
