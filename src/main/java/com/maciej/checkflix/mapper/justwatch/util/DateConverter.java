package com.maciej.checkflix.mapper.justwatch.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateConverter {

    public static LocalDate convertStringToLocalDate(String date) {
        return date == null ?
                null :
                LocalDate.of(
                        Integer.parseInt(date.split("-")[0]),
                        Integer.parseInt(date.split("-")[1]),
                        Integer.parseInt(date.split("-")[2]));
    }

    public static String convertLocalDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }
}
