package com.github.kevinrobayna;

import java.util.List;

class CronExpression {

    private static final String SPACE = " ";
    private static final String TAB = "\t";
    private static final String END_OF_LINE = "\n";

    private final List<String> minutes;
    private final List<String> hours;
    private final List<String> daysOfMonth;
    private final List<String> months;
    private final List<String> daysOfWeek;
    private final String command;

    CronExpression(List<String> minutes,
                   List<String> hours,
                   List<String> daysOfMonth,
                   List<String> months,
                   List<String> daysOfWeek,
                   String command) {
        this.minutes = minutes;
        this.hours = hours;
        this.daysOfMonth = daysOfMonth;
        this.months = months;
        this.daysOfWeek = daysOfWeek;
        this.command = command;
    }

    public List<String> getMinutes() {
        return minutes;
    }

    public List<String> getHours() {
        return hours;
    }

    public List<String> getDaysOfMonth() {
        return daysOfMonth;
    }

    public List<String> getMonths() {
        return months;
    }

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return "minute" + TAB + String.join(SPACE, minutes) + END_OF_LINE +
                "hour" + TAB + String.join(SPACE, hours) + END_OF_LINE +
                "day of month" + TAB + String.join(SPACE, daysOfMonth) + END_OF_LINE +
                "month" + TAB + String.join(SPACE, months) + END_OF_LINE +
                "day of week" + TAB + String.join(SPACE, daysOfWeek) + END_OF_LINE +
                "command" + TAB + command;
    }
}
