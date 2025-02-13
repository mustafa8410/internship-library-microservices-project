package com.example.webapi.service;

import com.example.webapi.entity.Book;
import com.example.webapi.entity.Bookcase;
import com.example.webapi.exceptions.book.BookNotFoundException;
import com.example.webapi.exceptions.global.InvalidParameterException;
import com.example.webapi.exceptions.global.MissingArgumentException;
import com.example.webapi.exceptions.bookcase.AllBookcasesFullException;
import com.example.webapi.exceptions.bookcase.BookcaseAlreadyFullException;
import com.example.webapi.exceptions.bookcase.BookcaseNotFoundException;
import com.example.webapi.repository.BookRepository;
import com.example.webapi.repository.BookcaseRepository;
import com.example.webapi.request.book.CreateBookRequest;
import com.example.webapi.request.book.UpdateBookRequest;
import com.example.webapi.response.BookResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookcaseRepository bookcaseRepository;

    public BookService(BookRepository bookRepository, BookcaseRepository bookcaseRepository) {
        this.bookRepository = bookRepository;
        this.bookcaseRepository = bookcaseRepository;
    }

    public BookResponse findBookById(Long bookId){
        return new BookResponse(bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new));
    }

    public List<BookResponse> findBooks(Optional<String> title, Optional<String> author, Optional<Long> bookcaseId,
                                        Optional<String> showRented){
        boolean showOnlyUnrentedBooks;
        if(showRented.isPresent() && showRented.get().equals("true"))
            showOnlyUnrentedBooks = false;
        else
            showOnlyUnrentedBooks = true;
        return bookRepository.findByFilterByTitleAuthorBookcaseId(title.orElse(null), author.orElse(null),
                bookcaseId.orElse(null),
                showOnlyUnrentedBooks).stream().map(BookResponse::new).collect(Collectors.toList());
    }

    public Long findCountOfBooks(Optional<Long> bookcaseId){
        if(bookcaseId.isPresent()){
            if(bookcaseRepository.existsBookcaseById(bookcaseId.get()))
                return bookRepository.countAllByBookcaseId(bookcaseId.get());
            else
                throw new BookcaseNotFoundException();

        }
        return bookRepository.count();
    }

    public List<BookResponse> getBooks(){
        return bookRepository.findAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    public BookResponse createBook(CreateBookRequest createBookRequest, String placeBook , Optional<Long> bookcaseId){
        if(createBookRequest.getAuthor().isEmpty() || createBookRequest.getTitle().isEmpty())
            throw new MissingArgumentException();
        Book newBook = new Book();
        newBook.setAuthor(createBookRequest.getAuthor());
        newBook.setDescription(createBookRequest.getDescription());
        newBook.setRegistrationDate(new Date());
        newBook.setTitle(createBookRequest.getTitle());
        if(placeBook.equals("true")){
            if(bookcaseId.isPresent()){
                Bookcase bookcase = bookcaseRepository.findById(bookcaseId.get())
                        .orElseThrow(BookcaseNotFoundException::new);
                Long bookCount = bookRepository.countAllByBookcaseId(bookcaseId.get());
                if(bookcase.getCapacity() <= bookCount){
                    bookRepository.save(newBook);
                    throw new BookcaseAlreadyFullException();
                }
                newBook.setBookcase(bookcase);
                return new BookResponse(bookRepository.save(newBook));
            }
            else {
                List<Bookcase> bookcaseList = bookcaseRepository.findAll();
                Long bookCount;
                for(Bookcase bookcase: bookcaseList){
                    bookCount = bookRepository.countAllByBookcaseId(bookcase.getId());
                    if(bookCount < bookcase.getCapacity()){
                        newBook.setBookcase(bookcase);
                        return new BookResponse(bookRepository.save(newBook));
                    }
                }
            }
            bookRepository.save(newBook);
            throw new AllBookcasesFullException();
        }
        else if(placeBook.equals("false"))
            return new BookResponse(bookRepository.save(newBook));
        else
            throw new InvalidParameterException("placeBook parameter is defined wrong: it only can be true or false.");
    }

    public BookResponse updateBook(UpdateBookRequest updateBookRequest) {
        Book book = bookRepository.findById(updateBookRequest.getBookId()).orElseThrow(BookNotFoundException::new);
        if(updateBookRequest.getNewBookcaseId() != null &&
                updateBookRequest.getNewBookcaseId() != book.getBookcase().getId()){
            Bookcase bookcase = bookcaseRepository.findById(updateBookRequest.getNewBookcaseId())
                    .orElseThrow(BookcaseNotFoundException::new);
            if(bookRepository.countAllByBookcaseId(bookcase.getId()) < bookcase.getCapacity())
                book.setBookcase(bookcase);
            else
                throw new BookcaseAlreadyFullException();
        }
        book.setTitle(updateBookRequest.getNewTitle());
        book.setDescription(updateBookRequest.getNewDesc());
        book.setAuthor(updateBookRequest.getNewAuthor());
        return new BookResponse(bookRepository.save(book));
    }

    public BookResponse placeBookIntoBookcaseById(Long bookId, Optional<Long> bookcaseId){
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        if(bookcaseId.isPresent()){
            Bookcase bookcase = bookcaseRepository.findById(bookcaseId.get())
                    .orElseThrow(BookcaseNotFoundException::new);
            if(bookRepository.countAllByBookcaseId(bookcaseId.get()) < bookcase.getCapacity())
                book.setBookcase(bookcase);
            else
                throw new BookcaseAlreadyFullException();

            return new BookResponse(bookRepository.save(book));
        }
        else {
            List<Bookcase> bookcaseList = bookcaseRepository.findAll();
            for(Bookcase bookcase: bookcaseList){
                if(bookRepository.countAllByBookcaseId(bookcase.getId()) < bookcase.getCapacity()){
                    book.setBookcase(bookcase);
                    return new BookResponse(bookRepository.save(book));
                }
            }
            throw new AllBookcasesFullException();
        }
    }

    public void deleteBookById(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

}
