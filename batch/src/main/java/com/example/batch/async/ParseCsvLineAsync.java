package com.example.batch.async;

import com.example.batch.excel.csvinput.BookExcel;
import com.example.batch.excel.csvinput.BookcaseExcel;
import com.example.batch.excel.csvinput.UserExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import com.example.batch.service.CsvService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ParseCsvLineAsync {

    private final CsvService csvService;

    public ParseCsvLineAsync(CsvService csvService) {
        this.csvService = csvService;
    }

    @Async
    public CompletableFuture<Boolean> parseBookLineAndSaveInstance(List<String> line){
        return CompletableFuture.supplyAsync( () -> {
            try {
                BookExcel bookExcel = new BookExcel(line);
                csvService.parseBookLineAndSaveInstance(bookExcel);
            }
            catch (Exception e) {
                log.error(e.getMessage() + String.join(";", line));
            }
            return true;
        } );

    }
    @Async
    public CompletableFuture<Boolean> parseBookcaseLineAndSaveInstance(List<String> line){
        return CompletableFuture.supplyAsync(() -> {
            try {
                BookcaseExcel bookcaseExcel = new BookcaseExcel(line);
                csvService.parseBookcaseLineAndSaveInstance(bookcaseExcel);
            }
            catch (Exception e) {
                log.error(e.getMessage() + String.join(";", line));
            }
            return true;
        });
    }
    @Async
    public CompletableFuture<Boolean> parseUserLineAndSaveInstance(List<String> line){
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserExcel userExcel = new UserExcel(line);
                csvService.parseUserLineAndSaveInstance(userExcel);
            }
            catch (Exception e) {
                log.error(e.getMessage() + String.join(";", line));
            }
            return true;
        });

    }
    /*@Async
    public CompletableFuture<Boolean> parseRentLineAndSaveInstance(List<String> line){
        try {
            csvService.parseRentLineAndSaveInstance(line);
        }
        catch (Exception e) {
            log.error(e.getMessage() + String.join(";", line), e);
        }
        return CompletableFuture.completedFuture(true);
    }*/


    /*@Async
    public CompletableFuture<Boolean> parseLineAndSaveInstance(List<String> line, EntityType entityType) {
        switch (entityType) {
            case BOOK:
                try {
                    csvService.parseBookLineAndSaveInstance(line);
                }
                catch (Exception e) {
                    log.error(e.getMessage() + String.join(";", line));
                }
                break;
            case BOOKCASE:
                try {
                    csvService.parseBookcaseLineAndSaveInstance(line);
                }
                catch (Exception e) {
                    log.error(e.getMessage() + String.join(";", line));
                }
                break;
            case USER:
                try {
                    csvService.parseUserLineAndSaveInstance(line);
                }
                catch (Exception e) {
                    log.error(e.getMessage() + String.join(";", line));
                }
                break;
        }


        return CompletableFuture.completedFuture(true);
    }*/








}
