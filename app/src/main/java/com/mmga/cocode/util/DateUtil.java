package com.mmga.cocode.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final long MINUTE = 60 * 1000;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final long MONTH = 30 * DAY;
    private static final long YEAR = 365 * DAY;


    static Date date;

    public static String parseDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeNow = System.currentTimeMillis();
        //8h时差
        return simpleDate(timeNow - date.getTime() - 8 * HOUR);


    }

    private static String simpleDate(long time) {
        if (time < 0) {
            return "时间出错";
        } else if (time < MINUTE) {
            return "刚刚";
        } else if (time < HOUR) {
            return "" + time / MINUTE + "分钟前";
        } else if (time < DAY) {
            return "" + time / HOUR + "小时前";
        } else if (time < MONTH) {
            return "" + time / DAY + "日前";
        } else if (time < YEAR) {
            return "" +  time / MONTH + "月前";
        } else {
            return "" + (int) time / YEAR + "年前";
        }

    }


}
