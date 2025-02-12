package com.example.batch.excel;

import com.example.batch.exception.csv.InvalidCsvLineException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserExcel {

    @CsvFileMapping(value = FieldName.NAME)
    String name;
    @CsvFileMapping(value = FieldName.SURNAME)
    String surname;
    @CsvFileMapping(value = FieldName.MAIL)
    String mail;
    @CsvFileMapping(value = FieldName.USERNAME)
    String username;
    @CsvFileMapping(value = FieldName.PASSWORD)
    String password;
    @CsvFileMapping(value = FieldName.ROLE)
    String role;

    public UserExcel(List<String> lines) throws Exception{
        if(lines.size() != 6)
            throw new InvalidCsvLineException("There can only be 6 fields in a user instance, but the following line has more or less: ");
        this.name = lines.get(0);
        this.surname = lines.get(1);
        this.mail = lines.get(2);
        this.username = lines.get(3);
        this.password = lines.get(4);
        this.role = lines.get(5);
    }

}
