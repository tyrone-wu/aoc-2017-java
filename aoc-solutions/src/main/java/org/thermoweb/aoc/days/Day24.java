package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(24)
public class Day24 implements Day {
    private static class Components {
        List<Pair<Integer, Integer>> components;

        private Components(String input) {
            this.components = new ArrayList<>();
            input.lines().forEach(line -> {
                String[] split = line.split("/");
                int l = Integer.parseInt(split[0]);
                int r = Integer.parseInt(split[1]);
                components.add(Pair.of(l, r));
            });
        }

        private Components(List<Pair<Integer, Integer>> components) {
            this.components = components;
        }

        private Components copyRemove(Pair<Integer, Integer> port) {
            Components copy = new Components(new ArrayList<>(this.components));
            copy.components.remove(port);
            return copy;
        }
    }

    private static Pair<Integer, Integer> link(Components components, int openPort, int depth, boolean p2) {
        int maxStrength = 0;
        int maxDepth = depth;
        for (var p : components.components) {
            int l = p.getLeft();
            int r = p.getRight();
            if (openPort == l || openPort == r) {
                var link = link(components.copyRemove(p), openPort == l ? r : l, depth + 1, p2);
                int linkStrength = l + r + link.getLeft();
                int linkDepth = link.getRight();
                if (!p2) {
                    maxStrength = Math.max(maxStrength, linkStrength);
                } else if (maxDepth == linkDepth) {
                    maxStrength = Math.max(maxStrength, linkStrength);
                } else if (maxDepth < linkDepth) {
                    maxStrength = linkStrength;
                    maxDepth = linkDepth;
                }
            }
        }
        return Pair.of(maxStrength, maxDepth);
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Components components = new Components(input);
        return Optional.of(BigInteger.valueOf(link(components, 0, 0, false).getLeft()));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Components components = new Components(input);
        // LongestBridge longest = new LongestBridge();
        return Optional.of(BigInteger.valueOf(link(components, 0, 0, true).getLeft()));
    }
}
