package com.api.comic_reader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

    /**
     * This method converts a string to a Date object.
     * The string should be in the format "dd-MM-yyyy".
     * If the string is not in the correct format, it returns null.
     *
     * @param dateInString the date as a string
     * @return the date as a Date object or null if the string is not in the correct format
     */
    public Date convertStringToDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * This method converts a Date object to a string.
     * The string is in the format "dd-MM-yyyy".
     *
     * @param date the date as a Date object
     * @return the date as a string
     */
    public String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    /**
     * This method returns the current date as a Date object.
     *
     * @return the current date
     */
    public Date getCurrentDate() {
        return new Date();
    }
}
