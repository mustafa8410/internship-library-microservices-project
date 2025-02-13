package com.example.batch.async;


import com.example.batch.entity.Rented;
import com.example.batch.entity.User;
import com.example.batch.excel.csvoutput.CsvRentalDataResponse;
import com.example.batch.repository.BookRepository;
import com.example.batch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ProcessRentalDataAsync {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ProcessRentalDataAsync(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Async
    public CompletableFuture<CsvRentalDataResponse> processRentalData(Rented rented) {
        CsvRentalDataResponse rentalDataResponse;
        try {
            rentalDataResponse = new CsvRentalDataResponse(rented);
        }
        catch (Exception e) {
            throw e;
        }
        return CompletableFuture.completedFuture(rentalDataResponse);
    }
}
