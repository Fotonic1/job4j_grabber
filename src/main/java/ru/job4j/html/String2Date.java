package ru.job4j.html;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class String2Date {

    public static LocalDateTime stringToDate(String s) {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("d MM yy, HH:mm");
        return LocalDateTime.parse(changeToFormat(s), form);
    }

    private static String changeToFormat(String s) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("d MM yy");
        if (s.contains("сегодня")) {
            s = s.replaceAll("сегодня", f.format(LocalDate.now()));
            return s;
        } else if (s.contains("вчера")) {
            s = s.replaceAll("вчера", f.format(LocalDate.now().minusDays(1)));
            return s;
        }
        s = s.replaceAll("янв", "01");
        s = s.replaceAll("фев", "02");
        s = s.replaceAll("мар", "03");
        s = s.replaceAll("апр", "04");
        s = s.replaceAll("май", "05");
        s = s.replaceAll("июн", "06");
        s = s.replaceAll("июл", "07");
        s = s.replaceAll("авг", "08");
        s = s.replaceAll("сен", "09");
        s = s.replaceAll("окт", "10");
        s = s.replaceAll("ноя", "11");
        s = s.replaceAll("дек", "12");
        return s;
    }
}
