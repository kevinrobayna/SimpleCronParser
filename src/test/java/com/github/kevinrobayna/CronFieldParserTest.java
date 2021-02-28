package com.github.kevinrobayna;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CronFieldParserTest {

    @Test
    public void testWildCarForMinuteReturnsExpectedRange() {
        var result = CronFieldParser.parse("*", CronFieldParser.CronField.MINUTE);

        Assertions.assertEquals(rangeToListOfString(IntStream.range(0, 60)), result);
    }

    @Test
    public void testWildCarForHourReturnsExpectedRange() {
        var result = CronFieldParser.parse("*", CronFieldParser.CronField.HOUR);

        Assertions.assertEquals(rangeToListOfString(IntStream.range(0, 24)), result);
    }

    @Test
    public void testWildCarForDayOfMonthReturnsExpectedRange() {
        var result = CronFieldParser.parse("*", CronFieldParser.CronField.DAY_OF_MONTH);

        Assertions.assertEquals(rangeToListOfString(IntStream.rangeClosed(1, 31)), result);
    }

    @Test
    public void testWildCarForDayOfWeekReturnsExpectedRange() {
        var result = CronFieldParser.parse("*", CronFieldParser.CronField.DAY_OF_WEEK);

        Assertions.assertEquals(rangeToListOfString(IntStream.range(0, 7)), result);
    }

    @Test
    public void testWildCarForMonthReturnsExpectedRange() {
        var result = CronFieldParser.parse("*", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(rangeToListOfString(IntStream.rangeClosed(1, 12)), result);
    }

    @Test
    public void testValidRangeReturnsExpectedResults() {
        var result = CronFieldParser.parse("1-5", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(List.of("1", "2", "3", "4", "5"), result);
    }

    @Test
    public void testFieldThatIsDigitSingleDigitIsReturned() {
        var result = CronFieldParser.parse("5", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(List.of("5"), result);
    }

    @Test
    public void testRangeWithInvalidStartDefaultsToZero() {
        var result = CronFieldParser.parse("?-5", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(List.of("0", "1", "2", "3", "4", "5"), result);
    }

    @Test
    public void testRangeWithInvalidStartAndEndDefaultsToZero() {
        var result = CronFieldParser.parse("?-?", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(List.of("0"), result);
    }

    @Test
    public void testListOfCommaSeparatedValues() {
        var result = CronFieldParser.parse("0,1,2,3,4,5", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(List.of("0", "1", "2", "3", "4", "5"), result);
    }

    @Test
    public void testEvery15Minutes() {
        var result = CronFieldParser.parse("*/15", CronFieldParser.CronField.MINUTE);

        Assertions.assertEquals(List.of("0", "15", "30", "45"), result);
    }

    @Test
    public void testEvery5Hours() {
        var result = CronFieldParser.parse("*/5", CronFieldParser.CronField.HOUR);

        Assertions.assertEquals(List.of("0", "5", "10", "15", "20"), result);
    }

    @Test
    public void testEvery3Months() {
        var result = CronFieldParser.parse("*/3", CronFieldParser.CronField.MONTH);

        Assertions.assertEquals(List.of("3", "6", "9", "12"), result);
    }

    @Test
    public void testEveryOtherDayOfTheWeek() {
        var result = CronFieldParser.parse("*/2", CronFieldParser.CronField.DAY_OF_WEEK);

        Assertions.assertEquals(List.of("0", "2", "4", "6"), result);
    }

    @Test
    public void testEvery10Days() {
        var result = CronFieldParser.parse("*/10", CronFieldParser.CronField.DAY_OF_MONTH);

        Assertions.assertEquals(List.of("10", "20", "30"), result);
    }

    private List<String> rangeToListOfString(IntStream range) {
        return range.mapToObj(String::valueOf).collect(Collectors.toList());
    }

}