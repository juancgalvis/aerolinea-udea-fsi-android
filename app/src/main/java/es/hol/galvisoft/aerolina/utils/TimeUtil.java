package es.hol.galvisoft.aerolina.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Creado por Juan Carlos el dia 12/07/2015.
 */
public class TimeUtil {

    public static final TimeZone SIMPLE_TIME_ZONE = new SimpleTimeZone(0, "simpletime-zone");
    public static final SimpleDateFormat HOUR_FORMATTER = new SimpleDateFormat("hh:mm a"),
            INTERVAL_FORMATTER = new SimpleDateFormat("hh:mm a"),
            HOUR_MILITAR_FORMATTER = new SimpleDateFormat("HH:mm"),
            DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy"),
            DATE_TIME_FORMATTER = new SimpleDateFormat("dd-MM-yyyy  hh:mm a"),
            FULL_DATE_TIME_FORMATTER = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss:SS");
    public static final long DAY_IN_MINUTES = 60 * 24;
    private static final String VALIDATION_EXPRESSION = "[0-2]*[0-9]:[0-5]*[0-9]";

    static {
        HOUR_FORMATTER.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
        HOUR_MILITAR_FORMATTER.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
        INTERVAL_FORMATTER.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
        DATE_FORMATTER.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
        DATE_TIME_FORMATTER.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
        FULL_DATE_TIME_FORMATTER.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
    }

    public static long timeToMinutes(long millis) {
        return millis / 60000;
    }

    public static long minutesToTime(long minutes) {
        return minutes * 60000;
    }

    public static long toMilis(int hour, int minutes) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long offset = cal.getTimeZone().getOffset(0);

        return todayMinutes(timeToMinutes(cal.getTimeInMillis() + offset));

        //return hour * 60 + minutes;
    }

    public static long toTime(int hour, int minutes) {
        return minutesToTime(toMilis(hour, minutes));
    }

    public static long todayMinutes(long anyMinute) {
        if (anyMinute < DAY_IN_MINUTES)
            return anyMinute;
        return anyMinute - todayStartMinutes();
    }

    public static long absoluteMinutes(long todayMinutes) {
        if (todayMinutes > DAY_IN_MINUTES)
            return todayMinutes;
        return todayStartMinutes() + todayMinutes;
    }

    public static long nowMillis() {
        return Calendar.getInstance().getTimeInMillis() + Calendar.getInstance().getTimeZone().getOffset(0);
    }

    public static long nowAbsoluteMinutes() {
        Calendar now = Calendar.getInstance();
        long offset = now.getTimeZone().getOffset(0);
        return timeToMinutes(now.getTimeInMillis() + offset);
    }

    /**
     * Este método permite agregar a la fecha actual un número de días, meses y años. Por otra parte, asigna una hora, minuto y segundos determinados. Los milisegundos son en cero.
     *
     * @param numYear  Número de años a agregar.
     * @param numMonth Número de meses a agregar.
     * @param numDay   Número de días a agregar.
     * @param hour     Hora a establecer
     * @param minute   Minuto a establecer
     * @param seconds  Segundo a establecer
     * @return
     */
    public static long addDateNowSetTime(int numYear, int numMonth, int numDay, int hour, int minute, int seconds) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, numDay);
        now.add(Calendar.MONTH, numMonth);
        now.add(Calendar.YEAR, numYear);

        now.set(Calendar.HOUR, hour);
        now.set(Calendar.MINUTE, minute);
        now.set(Calendar.SECOND, seconds);
        now.set(Calendar.MILLISECOND, 0);

        long offset = now.getTimeZone().getOffset(0);

        return now.getTimeInMillis() + offset;
    }

    public static long todayStartMinutes() {
        return timeToMinutes(todayStartAbsolute());
    }

    public static long todayStartAbsolute() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        long offset = c.getTimeZone().getOffset(0);

        return c.getTimeInMillis() + offset;
    }

    public static long weekAgoStartAbsolute() {
        return todayStartAbsolute() - 7 * 24 * 60 * 60 * 1000;
    }

    public static long nowSinceStartDayMinutes() {
        return nowAbsoluteMinutes() - todayStartMinutes();
    }

    public static long dayMinuteFromString(String str) {
        int h = getHour(str);
        int m = getMinute(str);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);

        long offset = cal.getTimeZone().getOffset(0);

        long min = timeToMinutes(cal.getTimeInMillis() + offset);

        return todayMinutes(min);
    }

    public static int getHour(String time) {
        if (time == null || !time.matches(VALIDATION_EXPRESSION))
            return -1;
        return Integer.valueOf(time.split(":")[0]);
    }

    public static int getMinute(String time) {
        if (time == null || !time.matches(VALIDATION_EXPRESSION))
            return -1;
        return Integer.valueOf(time.split(":")[1]);
    }

    public static int getHour(long timeOfDay) {
        return (int) (timeOfDay / 60);
    }

    public static int getMinute(long timeOfDay) {
        return (int) (timeOfDay % 60);
    }

    public static String toString(int hour, int minutes) {
        return HOUR_FORMATTER.format(TimeUtil.toTime(hour, minutes));
    }

    public static String minutesToTimeString(long minutes) {
        return INTERVAL_FORMATTER.format(TimeUtil.minutesToTime(minutes));
    }

    public static String stringDateToTimeString(String time) {
        return time.substring(11, 16);
    }

    public static String minutesToDateString(long minutes) {
        return DATE_FORMATTER.format(TimeUtil.minutesToTime(minutes));
    }

    public static String minutesToDateTimeString(long minutes) {
        return DATE_TIME_FORMATTER.format(TimeUtil.minutesToTime(minutes));
    }

    public static String millisToString(long millis) {
        return DATE_FORMATTER.format(millis);
    }

    public static String millisToFullDateTime(long millis) {
        return FULL_DATE_TIME_FORMATTER.format(millis);
    }

    public static String hourDayMillisToString(Long hourDayMillis) {

        Double dHhora = (hourDayMillis.doubleValue() / 60);
        Long hora = dHhora.longValue();
        Long minutos = hourDayMillis - (hora * 60);

        return hora + ":" + minutos.intValue();
    }

    public static long toMilis(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() + Calendar.getInstance().getTimeZone().getOffset(0);
    }

    public static long toMilis(int numYear, int numMonth, int numDay, int hour, int minute, int seconds) {
        Calendar now = Calendar.getInstance();
        now.set(numYear, numMonth, numDay, hour, minute, seconds);
        now.set(Calendar.MILLISECOND, 0);
        long offset = now.getTimeZone().getOffset(0);
        return now.getTimeInMillis() + offset;
    }

    public static String millisToDateTime(long millis) {
        return DATE_TIME_FORMATTER.format(millis);
    }

    public static String millisToDateTime(String millis) {
        return DATE_TIME_FORMATTER.format(millis);
    }

    public static String millisToDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return DATE_TIME_FORMATTER.format(calendar.getTimeInMillis());
    }

    public static String millisToTime(long millis) {
        return HOUR_MILITAR_FORMATTER.format(millis);
    }

    public static String formatDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);
        return DATE_FORMATTER.format(c.getTimeInMillis());
    }

    public static String formatHour(int hour, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeUtil.SIMPLE_TIME_ZONE);
        c.set(0, 0, 0, hour, minutes, 0);
        return HOUR_FORMATTER.format(c.getTimeInMillis());
    }

    public static boolean esNuevoDia(long minutesOne, long minutesTwo) {
        Calendar one = Calendar.getInstance();
        Calendar two = Calendar.getInstance();
        one.setTimeZone(SIMPLE_TIME_ZONE);
        two.setTimeZone(SIMPLE_TIME_ZONE);
        one.setTimeInMillis(minutesToTime(minutesOne));
        two.setTimeInMillis(minutesToTime(minutesTwo));
        one.set(Calendar.HOUR_OF_DAY, 0);
        one.set(Calendar.MINUTE, 0);
        one.set(Calendar.SECOND, 0);
        one.set(Calendar.MILLISECOND, 0);
        two.set(Calendar.HOUR_OF_DAY, 0);
        two.set(Calendar.MINUTE, 0);
        two.set(Calendar.SECOND, 0);
        two.set(Calendar.MILLISECOND, 0);
        return one.getTimeInMillis() < two.getTimeInMillis();
    }

    public static boolean isToday(long minutes) {
        return todayStartMinutes() <= minutes && minutes <= todayStartMinutes() + 24 * 60 - 1;
    }

    public static boolean isMorning(long minutes) {
        return todayStartMinutes() + 24 * 60 <= minutes && minutes <= todayStartMinutes() + 24 * 60 * 2;
    }

    public static long getDayMinutesFromMinutes(long minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(SIMPLE_TIME_ZONE);
        calendar.setTimeInMillis(minutes * 60 * 1000);
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
    }

    public static long tiempoInicioAlarma() {
        return nowAbsoluteMinutes() * 60000 + 5000;
    }
}

