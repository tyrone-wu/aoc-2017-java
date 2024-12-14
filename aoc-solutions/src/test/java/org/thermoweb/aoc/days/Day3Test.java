package org.thermoweb.aoc.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Exception;
import java.math.BigInteger;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DayRunner;

class Day3Test {
    private final Day day = new Day3();

    @Test
    void test_part_one() throws Exception {
        assertEquals(Optional.of(BigInteger.valueOf(31)), day.partOne(DayRunner.getExample(3)));
    }

    @Test
    void test_part_two() throws Exception {
        assertEquals(Optional.of(BigInteger.valueOf(747)), day.partTwo(DayRunner.getExample(3)));
    }
}
