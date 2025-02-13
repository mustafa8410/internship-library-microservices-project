package com.example.batch.excel.csvinput;

import com.example.batch.excel.CsvFileMapping;
import com.example.batch.excel.FieldName;
import com.example.batch.exception.csv.load.InvalidCsvLineException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookExcel {

    @CsvFileMapping(value = FieldName.TITLE)
    String title;
    @CsvFileMapping(value = FieldName.DESCRIPTION)
    String description;
    @CsvFileMapping(value = FieldName.AUTHOR)
    String author;
    @CsvFileMapping(value = FieldName.BOOKCASE_ID)
    Long bookcaseId;

    public BookExcel(List<String>lines) throws Exception {
        if(lines.size() != 4)
            throw new InvalidCsvLineException
                    ("There must be 4 fields in a book entity but the following line doesn't have that amount: ");
        this.title = lines.get(0);
        this.description = lines.get(1);
        this.author = lines.get(2);
        if(lines.get(3) != null && !lines.get(3).isEmpty())
            this.bookcaseId = Long.parseLong(lines.get(3));
        else
            this.bookcaseId = null;
    }


}
