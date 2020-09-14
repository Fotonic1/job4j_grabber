package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;

import java.util.ArrayList;
import java.util.List;


public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/").get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Post post = new Post();
                Element href = td.child(0);
                post.setLink(href.attr("href"));
                post.setName(href.text());
                rsl.add(detail(post));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post detail(Post post) {
        try {
            String href = post.getLink();
            Document doc = Jsoup.connect(href).get();
            Elements row = doc.select(".msgBody");
            post.setDescription(row.next().first().text());
            row = doc.select(".msgFooter");
            post.setCreated(String2Date.stringToDate(row.first().text().split(" \\[")[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
}
