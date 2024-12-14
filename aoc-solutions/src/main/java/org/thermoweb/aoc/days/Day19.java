package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(19)
public class Day19 implements Day {
    private enum Dir {
        U, R, D, L,
    }

    private static final Map<Dir, Pair<Integer, Integer>> DELTA = Map.of(
        Dir.U, Pair.of(0, -1),
        Dir.R, Pair.of(1, 0),
        Dir.D, Pair.of(0, 1),
        Dir.L, Pair.of(-1, 0)
    );

    private static class Maze {
        List<char[]> map;

        private Maze(String input) {
            this.map = input.lines().map(line -> line.toCharArray()).toList();
        }

        private Pair<Integer, Integer> getStart() {
            int x = -1;
            for (int i = 0; i < this.map.get(0).length; i++) {
                if (this.map.get(0)[i] == '|') {
                    x = i;
                    break;
                }
            }
            int y = 0;
            return Pair.of(x, y);
        }

        private char getSpace(int x, int y) {
            return this.map.get(y)[x];
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Maze map = new Maze(input);
        Pair<Integer, Integer> start = map.getStart();
        int x = start.getLeft();
        int y = start.getRight();
        Dir dir = Dir.D;
        List<Character> checkpoints = new ArrayList<>();
        while (map.getSpace(x, y) != ' ') {
            if (map.getSpace(x, y) == '+') {
                if (dir != Dir.D && map.getSpace(x, y - 1) != ' ') {
                    dir = Dir.U;
                } else if (dir != Dir.L && map.getSpace(x + 1, y) != ' ') {
                    dir = Dir.R;
                } else if (dir != Dir.U && map.getSpace(x, y + 1) != ' ') {
                    dir = Dir.D;
                } else if (dir != Dir.R && map.getSpace(x - 1, y) != ' ') {
                    dir = Dir.L;
                } else {
                    throw new RuntimeException(":(");
                }
            }
            char space = map.getSpace(x, y);
            if (space != '|' && space != '-' && space != '+') {
                checkpoints.add(space);
            }
            Pair<Integer, Integer> delta = DELTA.get(dir);
            x += delta.getLeft();
            y += delta.getRight();
        }
        StringBuilder letters = new StringBuilder(checkpoints.size());
        for (var c : checkpoints) {
            letters.append(c);
        }
        System.out.printf("p1: %s\n", letters.toString());
        return Optional.empty();
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Maze map = new Maze(input);
        Pair<Integer, Integer> start = map.getStart();
        int x = start.getLeft();
        int y = start.getRight();
        Dir dir = Dir.D;
        int steps = 0;
        while (map.getSpace(x, y) != ' ') {
            if (map.getSpace(x, y) == '+') {
                if (dir != Dir.D && map.getSpace(x, y - 1) != ' ') {
                    dir = Dir.U;
                } else if (dir != Dir.L && map.getSpace(x + 1, y) != ' ') {
                    dir = Dir.R;
                } else if (dir != Dir.U && map.getSpace(x, y + 1) != ' ') {
                    dir = Dir.D;
                } else if (dir != Dir.R && map.getSpace(x - 1, y) != ' ') {
                    dir = Dir.L;
                } else {
                    throw new RuntimeException(":(");
                }
            }
            Pair<Integer, Integer> delta = DELTA.get(dir);
            x += delta.getLeft();
            y += delta.getRight();
            steps += 1;
        }
        return Optional.of(BigInteger.valueOf(steps));
    }
}
