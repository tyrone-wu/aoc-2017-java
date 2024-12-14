package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(3)
public class Day3 implements Day {
    @Override
    public Optional<BigInteger> partOne(String input) {
        int steps = Integer.parseInt(input);
        int x = 0,y = 0;
        int dx = 1, dy = 1;
        int direction = 0;
        int i = 1;
        while (i < steps) {
            int dxt = 0, dyt = 0;
            switch (direction) {
                case 0:
                    dxt = Math.min(steps - i, dx);
                    dx += 1;
                    break;
                case 1:
                    dyt = Math.min(steps - i, dy);
                    dy += 1;
                    break;
                case 2:
                    dxt = -Math.min(steps - i, dx);
                    dx += 1;
                    break;
                default:
                    dyt = -Math.min(steps - i, dy);
                    dy += 1;
                    break;
            }
            x += dxt;
            y += dyt;
            i += Math.abs(dxt) + Math.abs(dyt);
            direction = (direction + 1) % 4;
        }
        return Optional.of(BigInteger.valueOf(Math.abs(x) + Math.abs(y)));
    }

    private static int squareSum(Map<Pair<Integer, Integer>, Integer> grid, int x, int y) {
        int sum = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((i == x && j == y) || !grid.containsKey(Pair.of(i, j))) {
                    continue;
                }
                sum += grid.get(Pair.of(i, j));
            }
        }
        return sum;
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        int target = Integer.parseInt(input);
        int x = 0, y = 0;
        int dx = 1, dy = 1;
        int direction = 0;
        HashMap<Pair<Integer, Integer>, Integer> grid = new HashMap<>();
        grid.put(Pair.of(0, 0), 1);
        while (true) {
            switch (direction) {
                case 0:
                    for (int i = 0; i < dx; i++) {
                        x += 1;
                        int squareSum = squareSum(grid, x, y);
                        if (squareSum > target) {
                            return Optional.of(BigInteger.valueOf(squareSum));
                        }
                        grid.put(Pair.of(x, y), squareSum);
                    }
                    dx += 1;
                    break;
                case 1:
                    for (int i = 0; i < dy; i++) {
                        y += 1;
                        int squareSum = squareSum(grid, x, y);
                        if (squareSum > target) {
                            return Optional.of(BigInteger.valueOf(squareSum));
                        }
                        grid.put(Pair.of(x, y), squareSum);
                    }
                    dy += 1;
                    break;
                case 2:
                    for (int i = 0; i < dx; i++) {
                        x -= 1;
                        int squareSum = squareSum(grid, x, y);
                        if (squareSum > target) {
                            return Optional.of(BigInteger.valueOf(squareSum));
                        }
                        grid.put(Pair.of(x, y), squareSum);
                    }
                    dx += 1;
                    break;
                default:
                    for (int i = 0; i < dy; i++) {
                        y -= 1;
                        int squareSum = squareSum(grid, x, y);
                        if (squareSum > target) {
                            return Optional.of(BigInteger.valueOf(squareSum));
                        }
                        grid.put(Pair.of(x, y), squareSum);
                    }
                    dy += 1;
                    break;
            }
            direction = (direction + 1) % 4;
        }
    }
}
