package com.amazonaws.dynamo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
    public static Long dateToEpocTimestamp(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()+86399999L;
    }

    public static String epocTimestampToDate(Long timestamp) throws ParseException {
        Date date = new Date(timestamp);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
