package com.example.batch.scheduled;

import com.example.batch.async.ProcessRentalDataAsync;
import com.example.batch.entity.Rented;
import com.example.batch.excel.csvoutput.CsvRentalDataResponse;
import com.example.batch.repository.RentedRepository;
import com.example.batch.service.CsvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WriteCsvScheduled {

    private final RentedRepository rentedRepository;
    private final ProcessRentalDataAsync processRentalDataAsync;
    private final CsvService csvService;

    public WriteCsvScheduled(RentedRepository rentedRepository, ProcessRentalDataAsync processRentalDataAsync,
                             CsvService csvService) {
        this.rentedRepository = rentedRepository;
        this.processRentalDataAsync = processRentalDataAsync;
        this.csvService = csvService;
    }

    @Scheduled(cron = "0 0 18 * * SUN")
    public void saveWeeklyRentalInfo(){
        log.info("Scheduled task to save daily rental data has started.");
        List<Rented> rentalList = getRentalData();
        List<CsvRentalDataResponse> responses = callAsyncRentalProcessing(rentalList);
        try {
            csvService.createCsvFileWithResponse(responses, CsvRentalDataResponse.class);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private List<Rented> getRentalData(){
        List<Rented> rentalList = rentedRepository.findAllSortByOrderByStartDateDesc();
        return rentalList;
    }

    private List<CsvRentalDataResponse> callAsyncRentalProcessing(List<Rented> rentedList) {
        List<CompletableFuture<CsvRentalDataResponse>> list = new ArrayList<>();
        for(Rented rented: rentedList)
            list.add(processRentalDataAsync.processRentalData(rented));
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        return list.stream().map(completableFuture -> {
            try {
                return completableFuture.get();
            }
            catch (Exception e) {
                log.info(e.getMessage(), e);
                return null;
            }
        }
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }


}
