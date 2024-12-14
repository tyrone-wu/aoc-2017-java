package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

// eoahnkljfipdcgbm

@DaySolver(16)
public class Day16 implements Day {
    private static String dance(String input, int cycles) {
        int len = 16;
        char[] progs = new char[len];
        for (int i = 0; i < len; i++) {
            progs[i] = (char) ('a' + i);
        }
        String[] danceSequence = input.split(",");
        Map<String, Integer> seen = new HashMap<>();
        for (int c = 0; c < cycles; c++) {
            for (var dance : danceSequence) {
                switch (dance.charAt(0)) {
                    case 's' -> {
                        int offset = Integer.parseInt(dance.substring(1));
                        char[] clone = progs.clone();
                        for (int i = offset; i < offset + len; i++) {
                            progs[i % len] = clone[i - offset];
                        }
                    }
                    case 'x' -> {
                        int i = Integer.parseInt(dance.substring(1, dance.indexOf('/')));
                        int j = Integer.parseInt(dance.substring(dance.indexOf('/') + 1, dance.length()));
                        char tmp = progs[i];
                        progs[i] = progs[j];
                        progs[j] = tmp;
                    }
                    case 'p' -> {
                        char l = dance.charAt(1);
                        char r = dance.charAt(3);
                        for (int i = 0; i < len; i++) {
                            if (progs[i] == r) {
                                progs[i] = l;
                            } else if (progs[i] == l) {
                                progs[i] = r;
                            }
                        }
                    }
                    default -> throw new RuntimeException("oopsie");
                }
            }
            String p = new String(progs);
            if (seen.containsKey(p)) {
                int seenCycle = seen.get(p);
                int cycleRange = c - seenCycle;
                if (cycles - c > cycleRange) {
                    c = ((cycles - c) / cycleRange) * cycleRange + seenCycle;
                }
            }
            seen.put(p, c);
        }
        return new String(progs);
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        String progs = dance(input, 1);
        System.out.printf("p1: %s\n", progs);
        return Optional.empty();
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        String progs = dance(input, 1000000000);
        System.out.printf("p2: %s\n", progs);
        return Optional.empty();
    }
}
