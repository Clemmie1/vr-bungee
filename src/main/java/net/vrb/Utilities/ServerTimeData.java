package net.vrb.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ServerTimeData {
    public static String getTimeData (){

        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return sdf.format(dateNow);

    }
}
