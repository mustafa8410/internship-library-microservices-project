package com.example.webapi.service;

import com.example.webapi.entity.Book;
import com.example.webapi.entity.Rented;
import com.example.webapi.entity.User;
import com.example.webapi.exceptions.book.BookNotFoundException;
import com.example.webapi.exceptions.rent.RentAlreadyExistsException;
import com.example.webapi.exceptions.rent.RentNotFoundException;
import com.example.webapi.repository.BookRepository;
import com.example.webapi.repository.RentedRepository;
import com.example.webapi.request.rent.CreateRentRequest;
import com.example.webapi.response.RentResponse;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentedService {

    private final RentedRepository rentedRepository;
    private final UserService userService;
    private final BookRepository bookRepository;
    private final UserDetailsServiceImplementation userDetailsService;

    public RentedService(RentedRepository rentedRepository, UserService userService, BookRepository bookRepository,
                         UserDetailsServiceImplementation userDetailsService) {
        this.rentedRepository = rentedRepository;
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userDetailsService = userDetailsService;
    }

    public RentResponse findRentById(Long id){
        Rented rent = rentedRepository.findById(id).orElseThrow(RentNotFoundException::new);
        userDetailsService.verifyUser(rent.getRenter());
        return new RentResponse(rent);
    }

    public List<RentResponse> findAllRentsByUserId(Long id){
        User user = userService.findUserById(id);
        return rentedRepository.findAllByRenter(user).stream().map(RentResponse::new).collect(Collectors.toList());
    }

    /*public Rented findRentByBookId(Long bookId){
        return rentedRepository.findByBookId(bookId).orElseThrow(RentNotFoundException::new);
    }*/

    public RentResponse createRent(CreateRentRequest createRentRequest){
        User user = userService.findUserById(createRentRequest.getUserId());
        Book book = bookRepository.findById(createRentRequest.getBookId()).orElseThrow(BookNotFoundException::new);
        if(rentedRepository.existsByBook(book))
            throw new RentAlreadyExistsException();
        Long numberOfDays;
        if(createRentRequest.getNumberOfDays() == null)
            numberOfDays = 7L;
        else
            numberOfDays = createRentRequest.getNumberOfDays();
        Rented rented = new Rented();
        rented.setBook(book);
        rented.setRenter(user);
        rented.setStartDate(Date.from(Instant.now()));
        rented.setEndDate(Date.from(Instant.now().plusSeconds(24 * 3600 * numberOfDays)));
        rented.setAlertSent(false);
        return new RentResponse(rentedRepository.save(rented));
    }

    public void deleteRentById(Long rentId){
        Rented rented = rentedRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
        userDetailsService.verifyUser(rented.getRenter());
        rentedRepository.delete(rented);
    }
}
