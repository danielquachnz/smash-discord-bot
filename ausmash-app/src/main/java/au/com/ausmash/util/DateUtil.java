package au.com.ausmash.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    final static Format FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    public static String dateToString(final Date date) {
        return FORMATTER.format(date);
    }
}