package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(15)
public class Day15 implements Day {
    private static class Generator {
        long value;
        long factor;
        long div;
        long criteria;

        private Generator(String input, long factor, long div, long criteria) {
            String[] split = input.split("\\s+");
            this.value = Long.parseLong(split[4]);
            this.factor = factor;
            this.div = div;
            this.criteria = criteria;
        }

        private long generateNext() {
            this.value = (this.value * this.factor) % this.div;
            while (this.value % this.criteria != 0) {
                this.value = (this.value * this.factor) % this.div;
            }
            return this.value;
        }
    }

    private static int matches(String input, int pairs, long criteriaA, long criteriaB) {
        List<String> lines = input.lines().toList();
        Generator a = new Generator(lines.get(0), 16807, 2147483647, criteriaA);
        Generator b = new Generator(lines.get(1), 48271, 2147483647, criteriaB);
        int matches = 0;
        for (int i = 0; i < pairs; i++) {
            if ((a.generateNext() & 0xffff) == (b.generateNext() & 0xffff)) {
                matches++;
            }
        }
        return matches;
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        return Optional.of(BigInteger.valueOf(matches(input, 40000000, 1, 1)));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        return Optional.of(BigInteger.valueOf(matches(input, 5000000, 4, 8)));
    }
}
