package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td: row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Element time = td.parent().child(5);
            System.out.println(stringToDate(time.text().toString()));
        }
    }

    public static Timestamp stringToDate(String s) {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("d MM yy, HH:mm");
        return Timestamp.valueOf(LocalDateTime.parse(changeToFormat(s), form));
    }

    public static String changeToFormat(String s) {
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
