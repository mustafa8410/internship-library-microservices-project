package com.example.batch.service;

import com.example.batch.entity.Book;
import com.example.batch.entity.Bookcase;
import com.example.batch.entity.User;
import com.example.batch.excel.CsvFileMapping;
import com.example.batch.excel.csvinput.BookExcel;
import com.example.batch.excel.csvinput.BookcaseExcel;
import com.example.batch.excel.csvinput.UserExcel;
import com.example.batch.excel.csvoutput.CsvOutputClassHolder;
import com.example.batch.excel.csvoutput.CsvRentalDataResponse;
import com.example.batch.exception.bookcase.BookcaseNotFoundException;
import com.example.batch.exception.csv.IncompatibleClassException;
import com.example.batch.exception.csv.load.InvalidCsvLineException;
import com.example.batch.exception.csv.save.UnsavableClassException;
import com.example.batch.exception.user.UserAlreadyExistsException;
import com.example.batch.repository.BookRepository;
import com.example.batch.repository.BookcaseRepository;
import com.example.batch.repository.RentedRepository;
import com.example.batch.repository.UserRepository;
import com.example.batch.response.BookResponse;
import com.example.batch.response.UserResponse;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CsvService {

    @Value("${csv.rental.save.path}")
    private String rentalSavePath;

    private final BookRepository bookRepository;
    private final BookcaseRepository bookcaseRepository;
    private final DateParserService dateParserService;
    private final UserRepository userRepository;
    private final RentedRepository rentedRepository;
    private final PasswordEncoder passwordEncoder;


    public CsvService(BookRepository bookRepository, BookcaseRepository bookcaseRepository,
                      DateParserService dateParserService, UserRepository userRepository,
                      RentedRepository rentedRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.bookcaseRepository = bookcaseRepository;
        this.dateParserService = dateParserService;
        this.userRepository = userRepository;
        this.rentedRepository = rentedRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void parseBookLineAndSaveInstance(BookExcel bookExcel){
        //List<String> fields = Arrays.asList(line.split(","));
        //String line = String.join(";", fields);
        //log.info("How many fields: " + fields.size());
        /*if(fields.size() < 3)
            throw new InvalidCsvLineException
            ("There are not enough fields in the line: '" + line + "' to create a book instance of.");
        if(fields.size() > 4)
            throw new InvalidCsvLineException("There are more than 4 lines in the line: '" + line + "' , thus a book instance could not be created from it.");*/
        Book book = new Book(bookExcel);
        if(bookExcel.getBookcaseId() != null){
            Bookcase bookcase = bookcaseRepository.findById(bookExcel.getBookcaseId())
                    .orElseThrow(BookcaseNotFoundException::new);
            if(bookRepository.countAllByBookcaseId(bookcase.getId()) < bookcase.getCapacity())
                book.setBookcase(bookcase);
            else
                log.warn("Bookcase with id: " + bookcase.getId() +
                        " is already full, so the new book can't be placed there for entity: " + bookExcel.toString());
        }
        book.setRegistrationDate(new Date());
        bookRepository.save(book);
        log.info("New book has been created and saved to the database: " + new BookResponse(book).toString());

    }

    public void parseBookcaseLineAndSaveInstance(BookcaseExcel bookcaseExcel){
        //String line = String.join(";", fields);
        Bookcase bookcase = new Bookcase(bookcaseExcel);
        bookcaseRepository.save(bookcase);
        log.info("New bookcase has been created and saved to the database: " + bookcase.toString());
    }
    public void parseUserLineAndSaveInstance(UserExcel userExcel){
        //String line = String.join(";", fields);
        if(!userExcel.getRole().equals("user") && !userExcel.getRole().equals("admin"))
            throw new InvalidCsvLineException
                    ("A user's role can only be admin or user, but it's not defined like that on line: ");
        if(userRepository.existsByMail(userExcel.getMail()))
            throw new UserAlreadyExistsException("A user with given email already exists for line: ");
        if(userRepository.existsByUsername(userExcel.getUsername()))
            throw new UserAlreadyExistsException("A user with given username already exists for line: ");
        User user = new User(userExcel);
        user.setPassword(passwordEncoder.encode(userExcel.getPassword()));
        userRepository.save(user);
        log.info("A new user entity has been created and saved to the database: " +  new UserResponse(user).toString());
    }
    /*public void parseRentLineAndSaveInstance(List<String> fields){
        //String line = String.join(";", fields);
        if(fields.size() != 5)
            throw new InvalidCsvLineException("There must be 5 fields for a rental entity, but the following line has more than that: ");
        Rented rented = new Rented();
        try {
            rented.setStartDate(dateParserService.parseDate(fields.get(0)));
            rented.setEndDate(dateParserService.parseDate(fields.get(1)));
        }
        catch (ParseException e) {
            log.error("Error has happened while parsing the date from .csv file: ", e.getMessage(), e);
            return;
        }
        User user = userRepository.findById(Long.parseLong(fields.get(2))).orElseThrow(UserNotFoundException::new);
        rented.setRenter(user);
        Book book = bookRepository.findById(Long.parseLong(fields.get(3))).orElseThrow(BookNotFoundException::new);
        if(rentedRepository.existsByBookId(book.getId()))
            throw new RentAlreadyExistsException("Book with given id already exists for line: ");
        rented.setBook(book);
        if(fields.get(4) == null || !fields.get(4).equals("true") || !fields.get(4).equals("false"))
            throw new InvalidCsvLineException("alertSent field should be assigned 'true' or 'false' but it's defined otherwise at line: ");
        rented.setAlertSent(Boolean.parseBoolean(fields.get(4)));
        rentedRepository.save(rented);
        log.info("A new rental entity has been created and saved to the database: " + new RentResponse(rented).toString());
    }*/

    public <T> void createCsvFileWithResponse(List<T> responseList, Class<T> classOfList){
        List<Class<?>> classList = new ArrayList<>(Arrays.asList(new CsvOutputClassHolder().getClasses()));
        List<String> header = getHeadersOfClass(classList, classOfList);
        if(header == null)
            throw new IncompatibleClassException("Provided class does not have any annotations suitable for this job.");
        if(responseList.get(0) instanceof CsvRentalDataResponse)
            createCsvFileWithRentalResponse((List<CsvRentalDataResponse>) responseList, header);
        else
            throw new UnsavableClassException("An unknown kind of response entities has been provided," +
                    " so no saving can be done.");
        log.info("File has been created and data written for class: " + classOfList.getName());
    }

    public void createCsvFileWithRentalResponse(List<CsvRentalDataResponse> responseList, List<String> header){
        String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +  ".csv";
        File folder = new File(Paths.get(System.getProperty("user.dir"), rentalSavePath).normalize().toString());
        File file = new File(folder, fileName);
        try(ICSVWriter writer = new CSVWriterBuilder(new FileWriter(file)).withSeparator(';').build()){
            writer.writeNext(header.toArray(new String[0]));
            for(CsvRentalDataResponse response: responseList){
                writer.writeNext(response.getFieldsArray());
                log.info("Line has been written into .csv for: " + response.toString());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info(fileName + " has been created and data written at: " +
                Paths.get(System.getProperty("user.dir"), rentalSavePath).normalize().toString());
    }

    <T> List<String> getHeadersOfClass(List<Class<?>> classList, Class<T> classOfList){
        List<String> headerList = new ArrayList<>();
        for(Class<?> clazz: classList) {
            if(clazz.equals(classOfList))
                for(Field field : classOfList.getDeclaredFields()) {
                    CsvFileMapping annotation = field.getAnnotation(CsvFileMapping.class);
                    if(annotation != null)
                        headerList.add(annotation.value().getVariations()[0]);
                }
                return headerList;

        }
        return null;
    }



}
