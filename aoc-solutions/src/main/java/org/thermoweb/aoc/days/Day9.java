package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(9)
public class Day9 implements Day {
    private static List<Character> sanitizeStream(char[] input) {
        List<Character> stream = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            if (input[i] == '!') {
                i++;
            } else {
                stream.add(input[i]);
            }
        }
        return stream;
    }

    private static Pair<Integer, Integer> solve(String input) {
        List<Character> stream = sanitizeStream(input.toCharArray());
        int score = 0;
        boolean garbage = false;
        int depth = 0;
        int garbageCount = 0;
        for (var c : stream) {
            if (garbage && c != '>') {
                garbageCount++;
            }
            if (c == '{' && !garbage) {
                depth++;
            } else if (c == '<') {
                garbage = true;
            } else if (c == '}' && !garbage) {
                score += depth;
                depth--;
            } else if (c == '>') {
                garbage = false;
            }
        }
        return Pair.of(score, garbageCount);
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        int score = 0;
        for (var line : input.lines().toList()) {
            score = solve(line).getLeft();
            // System.out.println(score);
        }
        return Optional.of(BigInteger.valueOf(score));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        int count = 0;
        for (var line : input.lines().toList()) {
            count = solve(line).getRight();
            // System.out.println(count);
        }
        return Optional.of(BigInteger.valueOf(count));
    }
}
