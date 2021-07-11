package ru.job4j.html;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        Map<String, String> monthLocal = new HashMap<>();
        monthLocal.put("янв", "01");
        monthLocal.put("фев", "02");
        monthLocal.put("мар", "03");
        monthLocal.put("апр", "04");
        monthLocal.put("май", "05");
        monthLocal.put("июн", "06");
        monthLocal.put("июл", "07");
        monthLocal.put("авг", "08");
        monthLocal.put("сен", "09");
        monthLocal.put("окт", "10");
        monthLocal.put("ноя", "11");
        monthLocal.put("дек", "12");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dataLocal = "0001-01-01";
        String[] arrayDate = parse.split(" ");
        if (arrayDate.length == 2) {
            if (arrayDate[0].equals("вчера,")) {
                dataLocal = LocalDate.now().minusDays(1).toString();
            } else if (arrayDate[0].equals("сегодня,")) {
                dataLocal = LocalDate.now().toString();
            }
            return LocalDateTime.parse(dataLocal + " " + arrayDate[1], formatter);
        } else if (arrayDate.length == 4) {
            String year = arrayDate[2];
            year = year.substring(0, year.length() - 1); // убрали запятую в конце года
            year = year.length() == 2 ? "20" + year : year; // делаем полную версию года
            String date = arrayDate[0];
            date = date.length() == 1 ? "0" + date : date; // делаем полную версию даты
            String month = monthLocal.get(arrayDate[1]); // заменяем короткое название месяца на номер месяца
            return LocalDateTime.parse(year + "-" + month + "-" + date + " " + arrayDate[3], formatter);
        }
        return null;
    }
}