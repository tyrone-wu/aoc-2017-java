package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(1)
public class Day1 implements Day {
    private static ArrayList<Byte> parseInput(String input) {
        ArrayList<Byte> digits = new ArrayList<>();
        for (char c : input.toCharArray()) {
            byte d = (byte) (c - '0');
            digits.add(d);
        }
        return digits;
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        ArrayList<Byte> digits = parseInput(input);
        long sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            int j = (i + 1) % digits.size();
            if (digits.get(i) == digits.get(j)) {
                sum += digits.get(i);
            }
        }
        return Optional.of(BigInteger.valueOf(sum));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        ArrayList<Byte> digits = parseInput(input);
        long sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            int j = (i + digits.size() / 2) % digits.size();
            if (digits.get(i) == digits.get(j)) {
                sum += digits.get(i);
            }
        }
        return Optional.of(BigInteger.valueOf(sum));
    }
}
