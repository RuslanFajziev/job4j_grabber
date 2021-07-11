package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PostParse {
    public static void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.getElementsByClass("msgTable").get(0).getElementsByTag("tr");
        for (Element td : row) {
            if (td.text().contains("Москва, до 200т")) {
                continue;
            } else if (td.text().contains("Цитировать Сообщить модератору")) {
                String data = td.text().substring(1, 16);
                System.out.println(data);
                continue;
            }
            String mess = td.getElementsByTag("tr").get(0).getElementsByTag("td").get(1).text();
            System.out.println(mess);
        }
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        parse(baseUrl);
    }
}
