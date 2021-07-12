package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class PostParse {
    public static void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String msbBody = doc.getElementsByClass("msgBody").get(1).text();
        String msbFooter = doc.getElementsByClass("msgFooter").get(0).text().substring(1, 16);
        System.out.println(msbBody);
        System.out.println(msbFooter);
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        parse(baseUrl);
    }
}