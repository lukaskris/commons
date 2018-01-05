package com.evodream.app.accountcommons.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Copied from Renseki's commons module
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DateUtil {

    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            new ThreadLocal<SimpleDateFormat>() {
                @SuppressLint("SimpleDateFormat")
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat();
                }
            };

    //    public static final String SQL_DATE = "EEE MMM dd hh:mm:ss 'GMT'XXX yyyy";
    public static final String SQL_DATE_ONLY = "yyyy-MM-dd";
    public static final String SQL_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_ONLY = "yyyy-MM-dd";
    public static final String DEFAULT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_ONLY = "dd/MM/yyyy" ;//"yyyy-MM-dd";
    public static final String DD_MM_YY = "dd/MM/yy";
    public static final String TIME_ONLY = "HH:mm:ss";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String HH = "HH";
    public static final String TIME_FORMAT = "HH:mm";

    public static Date stringToDate(String sDate) {
        //LOG.debug("String to Date");
        SimpleDateFormat sdf = dateFormat.get();
        try {
            sdf.applyPattern(FORMAT);
            return sdf.parse(sDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date stringToDate(String sDate, String format) {
        SimpleDateFormat sdf = dateFormat.get();
        try {
            sdf.applyPattern(format);
            return sdf.parse(sDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat df = dateFormat.get();
        df.applyPattern(FORMAT);
        return df.format(date);
    }


    public static String dateToString(Date date, String format) {
        SimpleDateFormat df = dateFormat.get();
        df.applyPattern(format);
        return df.format(date);
    }

    public static Date toCurrentTimezone(Date date, int timeZone){
//        long dateLong = date.getTime() + timeZone;
//        Date newDate = new Date(dateLong);
//        return newDate;
        return date;
    }

    public static Date toStandardTimezone(Date date, int timeZone){
//        long dateLong = date.getTime() - timeZone;
//        Date newDate = new Date(dateLong);
//        return newDate;
        return date;
    }

    public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
        long diffInMillis = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit,Long> result = new LinkedHashMap<>();
        long millisRest = diffInMillis;
        for ( TimeUnit unit : units ) {
            long diff = unit.convert(millisRest, TimeUnit.MILLISECONDS);
            long diffInMillisForUnit = unit.toMillis(diff);
            millisRest = millisRest - diffInMillisForUnit;
            result.put(unit,diff);
        }
        return result;
    }

    public static Date removeTimeInformation(Date datetime){
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(datetime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDateOnEarliestDayTime(Date dateTime) {
        return removeTimeInformation(dateTime);
    }

    public static Date getDateOnLatestDayTime(Date dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
