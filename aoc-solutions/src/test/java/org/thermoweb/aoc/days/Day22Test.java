package org.thermoweb.aoc.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Exception;
import java.math.BigInteger;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DayRunner;

class Day22Test {
    private final Day day = new Day22();

    @Test
    void test_part_one() throws Exception {
        assertEquals(Optional.of(BigInteger.valueOf(5587)), day.partOne(DayRunner.getExample(22)));
    }

    @Test
    void test_part_two() throws Exception {
        assertEquals(Optional.of(BigInteger.valueOf(2511944)), day.partTwo(DayRunner.getExample(22)));
    }
}
