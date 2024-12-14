package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(4)
public class Day4 implements Day {
    @Override
    public Optional<BigInteger> partOne(String input) {
        long valid = input.lines().filter(line -> {
                HashSet<String> seen = new HashSet<>();
                for (String s : line.split("\\s+")) {
                    if (seen.contains(s)) {
                        return false;
                    }
                    seen.add(s);
                }
                return true;
            }).count();
        return Optional.of(BigInteger.valueOf(valid));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        long valid = input.lines().filter(line -> {
                HashSet<List<Integer>> seen = new HashSet<>();
                for (String s : line.split("\\s+")) {
                    List<Integer> freq = new ArrayList<>(Collections.nCopies(26, 0));
                    for (var c : s.toCharArray()) {
                        freq.set(c - 'a', freq.get(c - 'a') + 1);
                    }
                    if (seen.contains(freq)) {
                        return false;
                    }
                    seen.add(freq);
                }
                return true;
            }).count();
        return Optional.of(BigInteger.valueOf(valid));
    }
}
