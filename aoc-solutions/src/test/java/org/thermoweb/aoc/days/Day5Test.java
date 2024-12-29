package org.thermoweb.aoc.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Exception;
import java.math.BigInteger;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DayRunner;

class Day5Test {
    private final Day day = new Day5();

    @Test
    void test_part_one() throws Exception {
        assertEquals(Optional.of(BigInteger.valueOf(5)), day.partOne(DayRunner.getExample(5)));
    }

    @Test
    void test_part_two() throws Exception {
        assertEquals(Optional.of(BigInteger.valueOf(10)), day.partTwo(DayRunner.getExample(5)));
    }
}
