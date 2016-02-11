package com.wesleyelliott.timetracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Wesley on 2016/02/10.
 */
public class StringUtil {

    public static String elapsedTimeToString(long elapsedTime) {
        Date date = new Date(elapsedTime);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(date);
    }
}
