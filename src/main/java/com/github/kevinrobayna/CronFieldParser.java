package com.github.kevinrobayna;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CronFieldParser {

    enum CronField {
        MINUTE,
        HOUR,
        DAY_OF_WEEK,
        MONTH,
        DAY_OF_MONTH
    }

    enum DayOfWeek {
        SUN(0),
        MON(1),
        TUE(2),
        WED(3),
        TRU(4),
        FRI(5),
        SAT(6);

        private int number;

        DayOfWeek(int number) {
            this.number = number;
        }

        public static DayOfWeek fromDayOfWeek(String dayOfWeek) {
            var result = Arrays.stream(DayOfWeek.values()).filter(day -> day.name().equals(dayOfWeek)).findFirst();
            if (result.isEmpty()) {
                throw new IllegalArgumentException("Unsupported day of the week");
            } else {
                return result.get();
            }
        }

    }

    private final static String ALL = "*";
    private final static String RANGE = "-";
    private final static String LIST = ",";
    private final static String STEP = "/";

    static List<String> parse(String fieldSource, CronField field) {
        if (fieldSource.equals(ALL)) {
            return fieldToStream(field)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList());
        } else if (isDigit(fieldSource)) {
            return List.of(fieldSource);
        } else if (isRange(fieldSource)) {
            var range = fieldSource.split(RANGE);
            if (field == CronField.DAY_OF_WEEK) {
                var start = DayOfWeek.fromDayOfWeek(range[0]).number;
                var end = DayOfWeek.fromDayOfWeek(range[1]).number;
                return IntStream
                        .rangeClosed(start, end)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.toList());
            }
            return IntStream
                    .rangeClosed(parseInt(range[0]), parseInt(range[1]))
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList());
        } else if (isList(fieldSource)) {
            return Arrays.stream(fieldSource.split(LIST))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        } else if (isStep(fieldSource)) {
            var step = fieldSource.split(STEP);
            var stepBy = parseInt(step[1]);
            return fieldToStream(field)
                    .filter(digit -> digit % stepBy == 0)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private static boolean isStep(String fieldSource) {
        return fieldSource.contains(STEP);
    }

    private static boolean isList(String fieldSource) {
        return fieldSource.contains(LIST);
    }

    private static Integer parseInt(String integer) {
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static boolean isDigit(String fieldSource) {
        try {
            Integer.parseInt(fieldSource);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRange(String fieldSource) {
        return fieldSource.contains(RANGE);
    }

    private static IntStream fieldToStream(CronField field) {
        switch (field) {
            case MINUTE:
                return IntStream.range(0, 60);
            case HOUR:
                return IntStream.range(0, 24);
            case MONTH:
                return IntStream.rangeClosed(1, 12);
            case DAY_OF_MONTH:
                return IntStream.rangeClosed(1, 31);
            case DAY_OF_WEEK:
                return IntStream.range(0, 7);
            default:
                throw new IllegalArgumentException("cron field not supported");
        }
    }


}
