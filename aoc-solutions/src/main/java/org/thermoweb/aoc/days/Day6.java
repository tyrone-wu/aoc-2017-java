package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(6)
public class Day6 implements Day {
    private static List<Integer> parseInput(String input) {
        List<Integer> banks = new ArrayList<>();
        for (var s : input.split("\\s+")) {
            banks.add(Integer.parseInt(s));
        }
        return banks;
    }

    private static int indexMax(List<Integer> banks) {
        int idx = 0;
        for (int i = idx + 1; i < banks.size(); i++) {
            if (banks.get(idx) < banks.get(i)) {
                idx = i;
            }
        }
        return idx;
    }

    private static List<Integer> redistribute(List<Integer> banks) {
        int maxIdx = indexMax(banks);
        int max = banks.get(maxIdx);
        List<Integer> newBanks = new ArrayList<>(banks);
        newBanks.set(maxIdx, 0);
        for (int i = 0; i < max; i++) {
            int idx = (maxIdx + i + 1) % newBanks.size();
            newBanks.set(idx, newBanks.get(idx) + 1);
        }
        return newBanks;
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        List<Integer> banks = parseInput(input);
        int cycles = 0;
        HashSet<List<Integer>> seen = new HashSet<>();
        while (!seen.contains(banks)) {
            seen.add(banks);
            banks = redistribute(banks);
            cycles++;
        }
        return Optional.of(BigInteger.valueOf(cycles));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        List<Integer> banks = parseInput(input);
        int cycles = 0;
        HashMap<List<Integer>, Integer> seen = new HashMap<>();
        while (!seen.containsKey(banks)) {
            seen.put(banks, cycles);
            banks = redistribute(banks);
            cycles++;
        }
        return Optional.of(BigInteger.valueOf(cycles - seen.get(banks)));
    }
}
