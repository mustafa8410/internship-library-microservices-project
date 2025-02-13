package com.example.batch.excel.csvinput;

import com.example.batch.excel.CsvFileMapping;
import com.example.batch.excel.FieldName;
import com.example.batch.exception.csv.load.InvalidCsvLineException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookcaseExcel {
    @CsvFileMapping(value = FieldName.CAPACITY)
    Long capacity;

    public BookcaseExcel(List<String> line) throws Exception{
        if(line.size() != 1)
            throw new InvalidCsvLineException
                    ("There can only be 1 field appointed to a bookcase, but the following line has more than 1: ");
        if(line.get(0) != null && !line.get(0).isEmpty())
            this.capacity = Long.parseLong(line.get(0));
        else
            this.capacity = 15L;
    }
}
