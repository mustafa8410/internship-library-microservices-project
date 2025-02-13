package com.example.batch.excel.csvinput;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class CsvInputClassHolder {
    private final List<Class<?>> classes = new ArrayList<>
            (Arrays.asList(BookExcel.class, BookcaseExcel.class, UserExcel.class));
}
