package com.example.batch.scheduled;

import com.example.batch.async.ParseCsvLineAsync;
import com.example.batch.excel.CsvFileMapping;
import com.example.batch.excel.FieldName;
import com.example.batch.excel.csvinput.*;
import com.example.batch.exception.csv.load.InvalidCsvLineException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;



@Component
@Slf4j
public class ReadCsvChecker {

    @Value("${csv.read.path}")
    private String csvPath;

    private final ParseCsvLineAsync parseCsvLineAsync;

    public ReadCsvChecker(ParseCsvLineAsync parseCsvLineAsync) {
        this.parseCsvLineAsync = parseCsvLineAsync;
    }

    @Scheduled(cron = "0 0 13 * * *")
    public void checkForCsv(){
        File folder = new File(Paths.get(System.getProperty("user.dir"), csvPath).normalize().toString());
        log.info("Started the scheduled check for .csv files for new book entries at: " + folder.getAbsolutePath());
        List<File> fileList = Arrays.asList(folder.listFiles(((dir, name) -> name.endsWith(".csv"))));
        List<List<String>> lines;
        for(File file: fileList) {
            log.info("Starting the process for file: " + file.getName());
            lines = parseLinesFromCsv(file);
            if(lines == null || lines.size() == 0 || lines.get(0) == null)
                log.error("Could not parse the lines from " + file.getName());
            else {
                try {
                    parseFirstLineAndCallAsync(lines);
                    log.info("File " + file.getName() + " has been processed.");
                    try {
                        Files.delete(file.toPath());
                        log.info("File " + file.getName() + " has been deleted.");
                    }
                    catch (Exception e) {
                        log.error("Exception happened while trying to delete the processed file: " +
                                file.getName() + " at: " + file.getAbsolutePath(), e);
                    }
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        log.info("Scheduled process for checking .csv files is complete.");

    }

    public List<List<String>> parseLinesFromCsv(File csvFile){
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).withCSVParser(parser).build()) {
            List<String[]> lines = csvReader.readAll();
            return lines.stream().map(line -> Arrays.asList(line)).collect(Collectors.toList());
        }
        catch (Exception e) {
            log.error("Error has happened for file: " + csvFile.getName() + "\n" + e.getMessage(), e);
        }
        return null;
    }

    public void parseFirstLineAndCallAsync(List<List<String>> lines) {
        /*List<String> headerList = lines.get(0).stream()
                .map(s -> s.replace("\uFEFF", "").trim()).collect(Collectors.toList());
        String joined = String.join(";", headerList);*/
        /*switch (joined) {
            case "title;desc;author;bookcase":
                callBookAsync(lines);
                break;
            case "capacity":
                callBookcaseAsync(lines);
                break;
            case "name;surname;mail;username;password;role":
                callUserAsync(lines);
                break;
            *//*case "startdate;enddate;renterid;bookid;alertsent":
                callRentAsync(lines);
                break;*//*
            default:
                throw new InvalidCsvFileException("First line of csv file is not supported in this application.");
        }*/
        /*EntityType entityType = EntityType.getEntityTypeFromHeader(lines.get(0)).orElseThrow(InvalidCsvFileException::new);
        List<CompletableFuture<Boolean>> asyncList = new ArrayList<>();
        for(List<String> line: lines.subList(1, lines.size()))
            asyncList.add(parseCsvLineAsync.parseLineAndSaveInstance(line, entityType));
        CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[0])).join();*/
        Class<?> clazz = getEntityTypeFromHeader(lines.get(0));
        if(clazz == null)
            throw new InvalidCsvLineException("Header of the .csv file does not match with any of the" +
                    " available entities.");
        //List<CompletableFuture<Boolean>> asyncList = new ArrayList<>();
        if(clazz == BookExcel.class)
            callBookAsync(lines);
        else if(clazz == BookcaseExcel.class)
          callBookcaseAsync(lines);
        else if(clazz == UserExcel.class)
            callUserAsync(lines);


    }

    public void callBookAsync(List<List<String>> lines){
        List<CompletableFuture<Boolean>> asyncList = new ArrayList<>();
        for(List<String> line: lines.subList(1, lines.size()))
            asyncList.add(parseCsvLineAsync.parseBookLineAndSaveInstance(line));
        CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[0])).join();
    }
    public void callBookcaseAsync(List<List<String>> lines){
        List<CompletableFuture<Boolean>> asyncList = new ArrayList<>();
        for(List<String> line: lines.subList(1, lines.size()))
            asyncList.add(parseCsvLineAsync.parseBookcaseLineAndSaveInstance(line));
        CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[0])).join();
    }
    public void callUserAsync(List<List<String>> lines){
        List<CompletableFuture<Boolean>> asyncList = new ArrayList<>();
        for(List<String> line: lines.subList(1, lines.size()))
            asyncList.add(parseCsvLineAsync.parseUserLineAndSaveInstance(line));
        CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[0])).join();
    }
    /*public void callRentAsync(List<List<String>> lines){
        List<CompletableFuture<Boolean>> asyncList = new ArrayList<>();
        for(List<String> line: lines.subList(1, lines.size()))
            asyncList.add(parseCsvLineAsync.parseRentLineAndSaveInstance(line));
        CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[0])).join();
    }*/

    private Class<?> getEntityTypeFromHeader(List<String> header) {
        List<Class<?>> classList = new CsvInputClassHolder().getClasses();
        for(Class<?> clazz: classList) {
            if(verifyHeadersForClass(clazz, header))
                return clazz;
        }
        return null;
    }

    private boolean verifyHeadersForClass(Class<?> clazz, List<String> header) {
        Field[] fields = clazz.getDeclaredFields();
        if(fields.length != header.size())
            return false;
        int i;
        for(i=0; i<fields.length; i++) {
            if(!FieldName.findStringInVariations(header.get(i), fields[i].getAnnotation(CsvFileMapping.class).value()))
                return false;
        }
        return true;
    }



}
