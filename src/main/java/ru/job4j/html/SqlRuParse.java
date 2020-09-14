package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SqlRuParse {
    public static void list() throws Exception {
        String2Date str2d = new String2Date();
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td: row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element time = td.parent().child(5);
                System.out.println(str2d.stringToDate(time.text()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String2Date str2d = new String2Date();
        String href = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        Document doc = Jsoup.connect(href).get();
        Elements row = doc.select(".msgBody");
        System.out.println(row.next().first().text());
        row = doc.select(".msgFooter");
        System.out.println(str2d.stringToDate(row.first().text().split(" \\[")[0]));
    }
}
