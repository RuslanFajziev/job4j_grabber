package ru.job4j.html;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {

    @Test
    public void test1() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = sqlRuDateTimeParser.parse("2 июл 21, 10:51");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime checkLocalDateTime = LocalDateTime.parse("2021-07-02 10:51", formatter);
        assertThat(localDateTime, is(checkLocalDateTime));
    }

    @Test
    public void test2() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = sqlRuDateTimeParser.parse("вчера, 10:51");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime checkLocalDateTime = LocalDateTime.parse(LocalDate.now().minusDays(1) + " 10:51", formatter);
        assertThat(localDateTime, is(checkLocalDateTime));
    }

    @Test
    public void test3() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = sqlRuDateTimeParser.parse("сегодня, 23:45");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime checkLocalDateTime = LocalDateTime.parse(LocalDate.now() + " 23:45", formatter);
        assertThat(localDateTime, is(checkLocalDateTime));
    }

    @Test
    public void test4() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = sqlRuDateTimeParser.parse("11 июл 2021, 10:45");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime checkLocalDateTime = LocalDateTime.parse("2021-07-11 10:45", formatter);
        assertThat(localDateTime, is(checkLocalDateTime));
    }

    @Test
    public void test5() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = sqlRuDateTimeParser.parse("11 июл 20 21, 10:45");
        assertNull(localDateTime);
    }
}