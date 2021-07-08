package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.getElementsByClass("ForumTable").get(0).getElementsByTag("tr");
        for (Element td : row) {
            if (td.text().contains("Тема Автор Ответов Просм. Дата")) {
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println("Ссылка                                           Тема                            Дата");
                System.out.println("-------------------------------------------------------------------------------------");
                continue;
            }
            Elements nm = td.getElementsByTag("tr").get(0).getElementsByTag("td");
            String ellink = nm.get(1).getElementsByTag("a").get(0).attr("href");
            String elSubject = nm.get(1).getElementsByTag("a").get(0).text();
            String elDate = nm.get(5).text();
            System.out.printf("%s - \"%s\" - (%s)\n", ellink, elSubject, elDate);
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }
}