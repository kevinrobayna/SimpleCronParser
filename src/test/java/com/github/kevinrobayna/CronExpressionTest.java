package com.github.kevinrobayna;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CronExpressionTest {

    private List<String> minutes;
    private List<String> hours;
    private List<String> daysOfMonth;
    private List<String> months;
    private List<String> daysOfWeek;
    private String command;
    private CronExpression cronExpression;

    @BeforeEach
    void setUp() {
        minutes = List.of("0", "15", "30", "45");
        hours = List.of("0");
        daysOfMonth = List.of("1", "15");
        months = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        daysOfWeek = List.of("1", "2", "3", "4", "5");
        command = "/usr/bin/find";

        cronExpression = new CronExpression(minutes, hours, daysOfMonth, months, daysOfWeek, command);
    }

    @Test
    void getMinutes() {
        Assertions.assertEquals(minutes, cronExpression.getMinutes());
    }

    @Test
    void getHours() {
        Assertions.assertEquals(hours, cronExpression.getHours());
    }

    @Test
    void getDaysOfMonth() {
        Assertions.assertEquals(daysOfMonth, cronExpression.getDaysOfMonth());
    }

    @Test
    void getMonths() {
        Assertions.assertEquals(months, cronExpression.getMonths());
    }

    @Test
    void getDaysOfWeek() {
        Assertions.assertEquals(daysOfWeek, cronExpression.getDaysOfWeek());
    }

    @Test
    void getCommand() {
        Assertions.assertEquals(command, cronExpression.getCommand());
    }

    @Test
    void testToString() {
        var expectedOutput = "minute" + "\t" + "0 15 30 45"  + "\n" +
                "hour" + "\t" + "0" + "\n" +
                "day of month" + "\t" + "1 15" + "\n" +
                "month" + "\t" + "1 2 3 4 5 6 7 8 9 10 11 12" + "\n" +
                "day of week" + "\t" + "1 2 3 4 5" + "\n" +
                "command" + "\t" + "/usr/bin/find";
        Assertions.assertEquals(expectedOutput, cronExpression.toString());
    }
}