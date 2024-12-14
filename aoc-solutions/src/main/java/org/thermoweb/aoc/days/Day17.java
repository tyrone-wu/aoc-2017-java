package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(17)
public class Day17 implements Day {
    @Override
    public Optional<BigInteger> partOne(String input) {
        int steps = Integer.parseInt(input);
        List<Integer> buffer = new ArrayList<>();
        buffer.add(0);
        int idx = 0;
        for (int i = 1; i <= 2017; i++) {
            idx = (idx + steps) % (buffer.size());
            idx++;
            buffer.add(idx, i);
        }
        return Optional.of(BigInteger.valueOf(buffer.get((idx + 1) % buffer.size())));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        int steps = Integer.parseInt(input);
        int idx = 0;
        int aferZero = 1;
        for (int i = 1; i <= 50000000; i++) {
            idx = (idx + steps) % i;
            if (idx == 0) {
                aferZero = i;
            }
            idx++;
        }
        return Optional.of(BigInteger.valueOf(aferZero));
    }
}
