package com.jingzhe.building.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class CoronaUtils {
    public String getTodayDate() {
        return getTime("yyyy-MM-dd");
    }

    public String getNextDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.plusDays(1).format(formatter);
    }

    public String getCurrentTime() {
        return getTime("yyyy-MM-dd HH:mm:ss");
    }
    
    private String getTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        return simpleDateFormat.format(new Date());
    }
    
}
