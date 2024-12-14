package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(7)
public class Day7 implements Day {
    private static String towerBottom(String input) {
        Set<String> left = new HashSet<>();
        Set<String> right = new HashSet<>();
        input.lines().forEach(line -> {
            String[] split = line.split(" -> ");
            String leftName = split[0].substring(0, split[0].indexOf(' '));
            left.add(leftName);
            if (split.length > 1) {
                String[] above = split[1].split(", ");
                right.addAll(Arrays.asList(above));
            }
        });
        left.removeAll(right);
        return left.iterator().next();
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        System.out.printf("part 1: %s\n", towerBottom(input));
        return Optional.of(BigInteger.valueOf(0));
    }

    static class Program {
        String name;
        int weight;
        String[] above;

        public Program(String input) {
            String[] split = input.split(" -> ");
            String name = split[0].substring(0, split[0].indexOf(' '));
            int weight = Integer.parseInt(split[0].substring(split[0].indexOf('(') + 1, split[0].indexOf(')')));
            this.name = name;
            this.weight = weight;
            this.above = split.length > 1 ? this.above = split[1].split(", ") : null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private static Map<String, Program> parseInput(String input) {
        Map<String, Program> tower = new HashMap<>();
        input.lines().forEach(line -> {
            Program p = new Program(line);
            tower.put(p.name, p);
        });
        return tower;
    }

    private static Pair<Integer, Boolean> inbalancedWeight(Map<String, Program> tower, String name) {
        Program p = tower.get(name);
        if (p.above == null) {
            return Pair.of(p.weight, false);
        }
        Map<Integer, Pair<Integer, String>> weights = new HashMap<>();
        for (var n : p.above) {
            Pair<Integer, Boolean> w = inbalancedWeight(tower, n);
            if (w.getRight()) {
                return w;
            }
            var tmp = weights.getOrDefault(w.getLeft(), Pair.of(0, n)).getLeft();
            weights.put(w.getLeft(), Pair.of(tmp + 1, n));
        }
        if (weights.size() > 1) {
            var iter = weights.entrySet().iterator();
            var l = iter.next();
            var r = iter.next();
            int w = l.getValue().getLeft() < r.getValue().getLeft() ? tower.get(l.getValue().getRight()).weight : tower.get(r.getValue().getRight()).weight;
            int diff = Math.abs(l.getKey() - r.getKey());

            return Pair.of(w - diff, true);
        }
        int sum = 0;
        for (var w : weights.entrySet()) {
            sum += w.getKey() * w.getValue().getLeft();
        }
        return Pair.of(p.weight + sum, false);
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Map<String, Program> tower = parseInput(input);
        String bottom = towerBottom(input);
        int inbalancedWeight = inbalancedWeight(tower, bottom).getLeft();
        return Optional.of(BigInteger.valueOf(inbalancedWeight));
    }
}
