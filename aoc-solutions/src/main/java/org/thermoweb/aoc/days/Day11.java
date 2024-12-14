package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(11)
public class Day11 implements Day {
    @Override
    public Optional<BigInteger> partOne(String input) {
        int x = 0, y = 0, z = 0;
        for (var mv : input.split(",")) {
            Triple<Integer, Integer, Integer> delta = switch (mv) {
                case "n" -> Triple.of(0, 1, -1);
                case "s" -> Triple.of(0, -1, 1);
                case "nw" -> Triple.of(-1, 1, 0);
                case "se" -> Triple.of(1, -1, 0);
                case "ne" -> Triple.of(1, 0, -1);
                case "sw" -> Triple.of(-1, 0, 1);
                default -> throw new RuntimeException("unexpected mv");
            };
            x += delta.getLeft();
            y += delta.getMiddle();
            z += delta.getRight();
        }
        return Optional.of(BigInteger.valueOf((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        int x = 0, y = 0, z = 0;
        int max = 0;
        for (var mv : input.split(",")) {
            Triple<Integer, Integer, Integer> delta = switch (mv) {
                case "n" -> Triple.of(0, 1, -1);
                case "s" -> Triple.of(0, -1, 1);
                case "nw" -> Triple.of(-1, 1, 0);
                case "se" -> Triple.of(1, -1, 0);
                case "ne" -> Triple.of(1, 0, -1);
                case "sw" -> Triple.of(-1, 0, 1);
                default -> throw new RuntimeException("unexpected mv");
            };
            x += delta.getLeft();
            y += delta.getMiddle();
            z += delta.getRight();
            max = Math.max(max, (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2);
        }
        return Optional.of(BigInteger.valueOf(max));
    }
}
