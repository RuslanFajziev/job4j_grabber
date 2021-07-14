package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public String getDescription(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        return doc.getElementsByClass("msgBody").get(1).text();
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> lstPost = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.getElementsByClass("ForumTable").get(0).getElementsByTag("tr");
        for (Element td : row) {
            if (td.text().contains("Тема Автор Ответов Просм. Дата") || td.text().contains("Важно:")) {
                continue;
            }
            Elements nm = td.getElementsByTag("tr").get(0).getElementsByTag("td");
            String elLink = nm.get(1).getElementsByTag("a").get(0).attr("href");
            String elTitle = nm.get(1).getElementsByTag("a").get(0).text();
            String elDate = nm.get(5).text();
            LocalDateTime elCreated = dateTimeParser.parse(elDate);
            String elDescription = getDescription(elLink);
            Post elmPost = new Post(elTitle, elLink, elDescription, elCreated);
            lstPost.add(elmPost);
        }
        return lstPost;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        String elDescription = doc.getElementsByClass("msgBody").get(1).text();
        String elDate = doc.getElementsByClass("msgFooter").get(0).text().substring(1, 16);
        LocalDateTime elCreated = dateTimeParser.parse(elDate);
        String msbHeader = doc.getElementsByClass("messageHeader").get(0).text();
        String elTitle = msbHeader.substring(0, msbHeader.length() - 6);
        return new Post(elTitle, link, elDescription, elCreated);
    }

    public static void main(String[] args) throws IOException {
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
        List<Post> lstPost = sqlRuParse.list("https://www.sql.ru/forum/job-offers");
        System.out.println(lstPost.size());
        System.out.println(lstPost.get(5));
        System.out.println(sqlRuParse.detail("https://www.sql.ru/forum/1337426/arhitektor-cifrovyh-modeley-ofis-zp-200-300k"));
    }
}