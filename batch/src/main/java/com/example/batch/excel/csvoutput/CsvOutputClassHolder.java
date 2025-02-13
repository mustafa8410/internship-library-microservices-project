package com.example.batch.excel.csvoutput;

import lombok.Data;

import java.util.List;

@Data
public class CsvOutputClassHolder {
    private final Class<?>[] classes = {CsvRentalDataResponse.class};
}
