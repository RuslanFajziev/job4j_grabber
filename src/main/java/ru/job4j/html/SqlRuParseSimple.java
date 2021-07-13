package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParseSimple {
    private static int numberRows = 0;

    public static void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.getElementsByClass("ForumTable").get(0).getElementsByTag("tr");
        for (Element td : row) {
            if (td.text().contains("Тема Автор Ответов Просм. Дата") || td.text().contains("Важно:")) {
                continue;
            }
            Elements nm = td.getElementsByTag("tr").get(0).getElementsByTag("td");
            String elLink = nm.get(1).getElementsByTag("a").get(0).attr("href");
            String elSubject = nm.get(1).getElementsByTag("a").get(0).text();
            String elDate = nm.get(5).text();
            System.out.printf("%s - \"%s\" - (%s)\n", elLink, elSubject, elDate);
            numberRows += 1;
        }
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://www.sql.ru/forum/job-offers";
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Ссылка                                           Тема                            Дата");
        System.out.println("-------------------------------------------------------------------------------------");
        for (int index = 1; index <= 5; index++) {
            String queryUrl = baseUrl + "/" + index;
            parse(queryUrl);
        }
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf("           Number of rows: %s%n", numberRows);
        System.out.println("-------------------------------------------------------------------------------------");
    }
}