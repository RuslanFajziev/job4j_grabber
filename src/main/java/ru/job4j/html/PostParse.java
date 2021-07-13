package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class PostParse {
    public static void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String msbBody = doc.getElementsByClass("msgBody").get(1).text();
        String msbFooter = doc.getElementsByClass("msgFooter").get(0).text().substring(0, 15);
        System.out.println(msbBody);
        System.out.println(msbFooter);
        String msbHeader = doc.getElementsByClass("messageHeader").get(0).text();
        System.out.println(msbHeader.substring(0, msbHeader.length() - 6));
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://www.sql.ru/forum/1337426/arhitektor-cifrovyh-modeley-ofis-zp-200-300k";
        parse(baseUrl);
        baseUrl = "https://www.sql.ru/forum/448729/priglashaem-sistemnogo-administratora-v-p-tomilino";
        parse(baseUrl);
    }
}