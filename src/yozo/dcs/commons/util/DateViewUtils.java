//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DateViewUtils {
    private static ThreadLocal<HashMap<String, SimpleDateFormat>> formatHolder = new ThreadLocal();
    public static final String SIMPLE_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String FULL_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN = "MM-dd HH:mm";
    public static final String DATE_FORMAT_MMddHHmm = "MM-dd HH:mm";
    public static final String FORMAT_HOUR = "HH:mm";
    public static final String FORMAT_SECOND = "HH:mm:ss";

    public DateViewUtils() {
    }

    public static String format(Date date, String pattern) {
        return date == null ? "" : getFormat(pattern).format(date);
    }

    public static String formatDate(Date date) {
        return date == null ? "" : getFormat("yyyy-MM-dd").format(date);
    }

    public static String formatFullDate(Date date) {
        return date == null ? "" : getFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatFullDate(long time) {
        return getFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    public static String formatFullDate(Long time) {
        return time == null ? "" : getFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    public static String simpleFormatDate(Date date) {
        return date == null ? "" : getFormat("MM-dd HH:mm").format(date);
    }

    public static String formatFullDateToday(Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }

        return getFormat("yyyy-MM-dd").format(date);
    }

    public static String formatHour(Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }

        return getFormat("HH:mm").format(date);
    }

    public static String formatSecond(Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }

        return getFormat("HH:mm:ss").format(date);
    }

    public static String formatDraftDate(Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }

        return getFormat("MM-dd HH:mm").format(date);
    }

    public static int compareForDays(Calendar cal1, Calendar cal2) {
        return isSameDay(cal1, cal2) ? 0 : cal1.compareTo(cal2);
    }

    public static int compareForDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return compareForDays(cal1, cal2);
    }

    public static boolean isExpiredForDays(Date date) {
        if (date == null) {
            return true;
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            if (cal1.compareTo(cal2) > 0) {
                return !isSameDay(cal1, cal2);
            } else {
                return false;
            }
        }
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(1) == cal2.get(1) && cal1.get(2) == cal2.get(2) && cal1.get(6) == cal2.get(6);
    }

    private static SimpleDateFormat getFormat(String key) {
        HashMap<String, SimpleDateFormat> map = (HashMap)formatHolder.get();
        if (map == null) {
            map = new HashMap(2);
            formatHolder.set(map);
        }

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)map.get(key);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(key);
            map.put(key, simpleDateFormat);
            formatHolder.set(map);
        }

        return simpleDateFormat;
    }

    public static String getDayBefore(int before) {
        Date d = new Date();
        Date date = new Date(d.getTime() - (long)(before * 24 * 60 * 60 * 1000));
        return getFormat("yyyy-MM-dd").format(date);
    }

    public static String getNow() {
        Date date = new Date();
        return getFormat("yyyy-MM-dd").format(date);
    }

    public static String yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, -1);
        return getFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String yesterdayFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, -1);
        return getFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, 1);
        return getFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String nextDayFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, 1);
        return getFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getNowFull() {
        Date date = new Date(System.currentTimeMillis());
        return getFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date parseFull(String date) {
        if (date != null && !"".equals(date)) {
            try {
                return getFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date parseSimple(String date) {
        try {
            return getFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException var2) {
            return null;
        }
    }

    public static Date parseSimpleDate(String date) {
        try {
            return getFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException var2) {
            return new Date();
        }
    }

    public static String getShowText(Date time) {
        if (time == null) {
            return "";
        } else {
            long between = Math.abs(System.currentTimeMillis() - time.getTime());
            if (between < 60000L) {
                return "刚刚";
            } else {
                long min = between / 1000L / 60L % 60L;
                long hour = between / 1000L / 60L / 60L % 24L;
                long day = between / 1000L / 60L / 60L / 24L;
                long month = day / 31L;
                if (min > 0L && hour == 0L && day == 0L && month == 0L) {
                    return min + "分钟前";
                } else if (hour > 0L && day == 0L && month == 0L) {
                    return hour + "小时前";
                } else if (day > 0L && month == 0L) {
                    return day + "天前";
                } else {
                    return month > 0L ? month + "个月前" : formatFullDate(time);
                }
            }
        }
    }

    public static long differenceTimeMin(long time1, long time2) {
        long d = time1 - time2;
        long minute = d / 60000L;
        return minute;
    }

    public static Date formatDateTime(Long date) {
        if (date == null) {
            return null;
        } else {
            String tmpDate = formatFullDate(date);
            return date != null && !"".equals(date) ? parseFull(tmpDate) : null;
        }
    }

    public static void main(String[] args) {
        Date a = parseFull("2014-05-22 20:00:00");
        Date b = parseFull("2014-05-22 00:00:00");
        System.out.println(differenceTimeMin(b.getTime(), a.getTime()));
    }
}
