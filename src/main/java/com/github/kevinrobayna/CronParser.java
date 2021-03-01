package com.github.kevinrobayna;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CronParser {

    private static final int MINUTES = 0;
    private static final int HOURS = 1;
    private static final int DAYS_OF_MONTH = 2;
    private static final int MONTHS = 3;
    private static final int DAYS_OF_WEEK = 4;

    static CronExpression parse(String cronExpression) {
        var splitExpression = cronExpression.split((" +"));
        var minutes = parseMinutes(splitExpression[MINUTES]);
        var hours = parseHours(splitExpression[HOURS]);
        var daysOfMonth = parseDaysOfMonth(splitExpression[DAYS_OF_MONTH]);
        var months = parseMonths(splitExpression[MONTHS]);
        var daysOfWeek = parseDaysOfWeek(splitExpression[DAYS_OF_WEEK]);
        var command = IntStream.range(0, splitExpression.length)
                .filter(index -> index > DAYS_OF_WEEK)
                .mapToObj(index -> splitExpression[index])
                .collect(Collectors.joining(" "));
        return new CronExpression(minutes, hours, daysOfMonth, months, daysOfWeek, command);
    }

    private static List<String> parseMinutes(String field) {
        return CronFieldParser.parse(field, CronFieldParser.CronField.MINUTE);
    }

    private static List<String> parseHours(String field) {
        return CronFieldParser.parse(field, CronFieldParser.CronField.HOUR);
    }

    private static List<String> parseDaysOfMonth(String field) {
        return CronFieldParser.parse(field, CronFieldParser.CronField.DAY_OF_MONTH);
    }

    private static List<String> parseMonths(String field) {
        return CronFieldParser.parse(field, CronFieldParser.CronField.MONTH);
    }

    private static List<String> parseDaysOfWeek(String field) {
        return CronFieldParser.parse(field, CronFieldParser.CronField.DAY_OF_WEEK);
    }

    public static void main(String... args) {
        var cronExpression = CronParser.parse(String.join(" ", args));
        System.out.println(cronExpression);
    }
}

