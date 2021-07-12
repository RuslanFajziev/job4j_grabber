package ru.job4j.html;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"),
            Map.entry("фев", "02"),
            Map.entry("мар", "03"),
            Map.entry("апр", "04"),
            Map.entry("май", "05"),
            Map.entry("июн", "06"),
            Map.entry("июл", "07"),
            Map.entry("авг", "08"),
            Map.entry("сен", "09"),
            Map.entry("окт", "10"),
            Map.entry("ноя", "11"),
            Map.entry("дек", "12")
    );
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime parse(String parse) {
        String dataLocal = "0001-01-01";
        String[] arrayDate = parse.split(" ");
        if (arrayDate.length == 2) {
            if (arrayDate[0].equals("вчера,")) {
                dataLocal = LocalDate.now().minusDays(1).toString();
            } else if (arrayDate[0].equals("сегодня,")) {
                dataLocal = LocalDate.now().toString();
            }
            return LocalDateTime.parse(dataLocal + " " + arrayDate[1], FORMATTER);
        } else if (arrayDate.length == 4) {
            String year = arrayDate[2];
            year = year.substring(0, year.length() - 1); // убрали запятую в конце года
            year = year.length() == 2 ? "20" + year : year; // делаем полную версию года
            String date = arrayDate[0];
            date = date.length() == 1 ? "0" + date : date; // делаем полную версию даты
            String month = MONTHS.get(arrayDate[1]); // заменяем короткое название месяца на номер месяца
            return LocalDateTime.parse(year + "-" + month + "-" + date + " " + arrayDate[3], FORMATTER);
        }
        return null;
    }
}