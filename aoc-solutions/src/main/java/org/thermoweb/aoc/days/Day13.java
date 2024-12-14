package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(13)
public class Day13 implements Day {
    private static Map<Integer, Triple<Integer, Integer, Boolean>> parseInput(String input) {
        Map<Integer, Triple<Integer, Integer, Boolean>> firewall = new HashMap<>();
        input.lines().forEach(line -> {
            String[] split = line.split(": ");
            firewall.put(Integer.parseInt(split[0]), Triple.of(Integer.parseInt(split[1]), 0, true));
        });
        return firewall;
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Map<Integer, Triple<Integer, Integer, Boolean>> firewall = parseInput(input);
        int end = firewall.keySet().stream().max(Integer::compare).get();
        int severity = 0;
        for (int ps = 0; ps <= end; ps++) {
            if (firewall.containsKey(ps)) {
                Triple<Integer, Integer, Boolean> wall = firewall.get(ps);
                if (wall.getMiddle() == 0) {
                    severity += ps * wall.getLeft();
                }
            }
            for (var entry : firewall.entrySet()) {
                {
                    Triple<Integer, Integer, Boolean> v = entry.getValue();
                    entry.setValue(Triple.of(v.getLeft(), v.getMiddle() + (v.getRight() ? 1 : -1), v.getRight()));
                }
                Triple<Integer, Integer, Boolean> v = entry.getValue();
                if (v.getMiddle() == 0 || v.getMiddle() == v.getLeft() - 1) {
                    entry.setValue(Triple.of(v.getLeft(), v.getMiddle(), !v.getRight()));
                }
            }
        }
        return Optional.of(BigInteger.valueOf(severity));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Map<Integer, Triple<Integer, Integer, Boolean>> firewall = parseInput(input);
        int ps = 0;
        while (true) {
            boolean notCaught = true;
            for (var entry : firewall.entrySet()) {
                int depth = entry.getKey();
                int range = entry.getValue().getLeft() * 2 - 2;
                if ((depth + ps) % range == 0) {
                    notCaught = false;
                    break;
                }
            }
            if (notCaught) {
                return Optional.of(BigInteger.valueOf(ps));
            }
            ps++;
        }
        // return Optional.empty();
    }
}
