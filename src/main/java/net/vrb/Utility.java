package net.vrb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utility {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Date getDate() {
        return new Date();
    }
    public static String getFutureTimestamp(int duration, TimeUnit unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) unit.toSeconds(duration));
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static boolean isPastTimestamp(String timestamp) {
        try {
            Date currentDate = new Date();
            Date targetDate = DATE_FORMAT.parse(timestamp);
            return targetDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getRemainingSeconds(String timestamp) {
        try {
            Date currentDate = new Date();
            Date targetDate = DATE_FORMAT.parse(timestamp);
            long diff = targetDate.getTime() - currentDate.getTime();
            return Math.max(TimeUnit.MILLISECONDS.toSeconds(diff), 0);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
