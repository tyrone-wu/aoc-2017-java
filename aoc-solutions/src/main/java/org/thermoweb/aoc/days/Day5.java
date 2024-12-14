package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(5)
public class Day5 implements Day {
    @Override
    public Optional<BigInteger> partOne(String input) {
        int[] jumps = input.lines().mapToInt(n -> Integer.parseInt(n)).toArray();
        int steps = 0;
        int offset = 0;
        while (offset < jumps.length) {
            int jump = jumps[offset];
            jumps[offset]++;
            offset += jump;
            steps++;
        }
        return Optional.of(BigInteger.valueOf(steps));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        int[] jumps = input.lines().mapToInt(n -> Integer.parseInt(n)).toArray();
        int steps = 0;
        int offset = 0;
        while (offset < jumps.length) {
            int jump = jumps[offset];
            if (jump >= 3) {
                jumps[offset]--;
            } else {
                jumps[offset]++;
            }
            offset += jump;
            steps++;
        }
        return Optional.of(BigInteger.valueOf(steps));
    }
}
