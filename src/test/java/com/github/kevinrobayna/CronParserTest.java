package com.github.kevinrobayna;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CronParserTest {

    @Test
    void parseAtAnyMinuteExpression() {
        // https://crontab.guru/#*_*_*_*_*
        var expression = CronParser.parse("* * * * * echo Hello");

        Assertions.assertEquals(rangeToListOfString(IntStream.range(0, 60)), expression.getMinutes());
        Assertions.assertEquals(rangeToListOfString(IntStream.range(0, 24)), expression.getHours());
        Assertions.assertEquals(rangeToListOfString(IntStream.rangeClosed(1, 31)), expression.getDaysOfMonth());
        Assertions.assertEquals(rangeToListOfString(IntStream.rangeClosed(1, 12)), expression.getMonths());
        Assertions.assertEquals(rangeToListOfString(IntStream.range(0, 7)), expression.getDaysOfWeek());
        Assertions.assertEquals("echo Hello", expression.getCommand());
    }

    @Test
    void parseExceptionWithMultipleTypes() {
        // https://crontab.guru/#*/15_0_1,15_*_1-5
        var expression = CronParser.parse("*/15 0 1,15 * 1-5 /usr/bin/find");

        Assertions.assertEquals(List.of("0", "15", "30", "45"), expression.getMinutes());
        Assertions.assertEquals(List.of("0"), expression.getHours());
        Assertions.assertEquals(List.of("1", "15"), expression.getDaysOfMonth());
        Assertions.assertEquals(rangeToListOfString(IntStream.rangeClosed(1, 12)), expression.getMonths());
        Assertions.assertEquals(rangeToListOfString(IntStream.rangeClosed(1, 5)), expression.getDaysOfWeek());
        Assertions.assertEquals("/usr/bin/find", expression.getCommand());
    }

    private List<String> rangeToListOfString(IntStream range) {
        return range.mapToObj(String::valueOf).collect(Collectors.toList());
    }
}