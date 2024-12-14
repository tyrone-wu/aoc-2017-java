package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(14)
public class Day14 implements Day {
    @Override
    public Optional<BigInteger> partOne(String input) {
        int ones = 0;
        for (int i = 0; i < 128; i++) {
            BigInteger hash = Day10.finalHash(input.concat(String.format("-%d", i)));
            ones += hash.bitCount();
        }
        return Optional.of(BigInteger.valueOf(ones));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Set<Pair<Integer, Integer>> used = new HashSet<>();
        for (int i = 0; i < 128; i++) {
            BigInteger hash = Day10.finalHash(input.concat(String.format("-%d", i)));
            char[] binary = hash.toString(2).toCharArray();
            int offset = 128 - binary.length;
            for (int j = offset; j < 128; j++) {
                if (binary[j - offset] == '1') {
                    used.add(Pair.of(j, i));
                }
            }
        }
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        int groups = 0;
        List<Pair<Integer, Integer>> delta = List.of(Pair.of(1, 0), Pair.of(-1, 0), Pair.of(0, 1), Pair.of(0, -1));
        for (var u : used) {
            boolean newGroup = false;
            if (!visited.contains(u)) {
                newGroup = true;
                visited.add(u);
                Queue<Pair<Integer, Integer>> bfs = new LinkedList<>();
                bfs.add(u);
                while (!bfs.isEmpty()) {
                    Pair<Integer, Integer> p = bfs.remove();
                    int x = p.getLeft(), y = p.getRight();
                    for (var d : delta) {
                        Pair<Integer, Integer> mv = Pair.of(x + d.getLeft(), y + d.getRight());
                        if (used.contains(mv) && !visited.contains(mv)) {
                            visited.add(mv);
                            bfs.add(mv);
                        }
                    }
                }
            }
            if (newGroup) {
                groups++;
            }
        }
        return Optional.of(BigInteger.valueOf(groups));
    }
}
