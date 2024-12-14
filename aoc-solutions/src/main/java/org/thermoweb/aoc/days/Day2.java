package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(2)
public class Day2 implements Day {
    private static List<List<Integer>>  parseInput(String input) {
        List<List<Integer>> grid = new ArrayList<>();
        input.lines().forEach(line -> {
            List<Integer> row = new ArrayList<>();
            for (String s : line.split("\\s+")) {
                row.add(Integer.parseInt(s));
            }
            grid.add(row);
        });
        return grid;
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        List<List<Integer>> grid = parseInput(input);
        long sum = 0;
        for (var row : grid) {
            sum += Collections.max(row) - Collections.min(row);
        }
        return Optional.of(BigInteger.valueOf(sum));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        List<List<Integer>> grid = parseInput(input);
        long sum = grid.stream().mapToLong(row -> {
            for (int i = 0; i < row.size(); i++) {
                for (int j = i + 1; j < row.size(); j++) {
                    if (row.get(i) % row.get(j) == 0) {
                        return row.get(i) / row.get(j);
                    } else if (row.get(j) % row.get(i) == 0) {
                        return row.get(j) / row.get(i);
                    }
                }
            }
            throw new IllegalStateException("no pairs that are evenly divisible");
        }).sum();
        return Optional.of(BigInteger.valueOf(sum));
    }
}
