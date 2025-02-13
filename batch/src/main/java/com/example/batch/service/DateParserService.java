package com.example.batch.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateParserService {
    private static final ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd") );

    public Date parseDate(String date) throws ParseException {
        return simpleDateFormatThreadLocal.get().parse(date);
    }
}
