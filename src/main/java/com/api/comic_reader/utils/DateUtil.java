package com.api.comic_reader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

    public Date convertStringToDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            return null;
        }
    }

    public String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public Date getCurrentDate() {
        return new Date();
    }
}
