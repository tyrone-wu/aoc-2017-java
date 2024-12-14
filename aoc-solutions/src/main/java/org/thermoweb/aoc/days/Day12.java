package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(12)
public class Day12 implements Day {
    private static Map<Integer, Set<Integer>> parseInput(String input) {
        Map<Integer, Set<Integer>> pipes = new HashMap<>();
        input.lines().forEach(line -> {
            String[] split = line.split(" <-> ");
            int id = Integer.parseInt(split[0]);
            Set<Integer> connected = new HashSet<>();
            Arrays.stream(split[1].split(", ")).mapToInt(Integer::parseInt).forEach(i -> connected.add(i));

            if (!pipes.containsKey(id)) {
                pipes.put(id, new HashSet<>());
            }
            pipes.get(id).addAll(connected);
            for (var i : connected) {
                if (!pipes.containsKey(i)) {
                    pipes.put(i, new HashSet<>());
                }
                pipes.get(i).add(id);
            }
        });
        return pipes;
    }

    private static Set<Integer> groupPipe(Map<Integer, Set<Integer>> pipes, Set<Integer> visited, int start) {
        Set<Integer> group = new HashSet<>();
        if (visited.contains(start)) {
            return group;
        }
        group.add(start);
        visited.add(start);

        Queue<Integer> bfs = new LinkedList<>();
        bfs.add(start);
        while (!bfs.isEmpty()) {
            int id = bfs.remove();
            for (var cId : pipes.get(id)) {
                if (visited.contains(cId)) {
                    continue;
                }
                bfs.add(cId);
                visited.add(cId);
                group.add(cId);
            }
        }
        return group;
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Map<Integer, Set<Integer>> pipes = parseInput(input);
        return Optional.of(BigInteger.valueOf(groupPipe(pipes, new HashSet<>(), 0).size()));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Map<Integer, Set<Integer>> pipes = parseInput(input);
        Set<Integer> visited = new HashSet<>();
        int groups = 0;
        for (int id : pipes.keySet()) {
            if (!groupPipe(pipes, visited, id).isEmpty()) {
                groups++;
            }
        }
        return Optional.of(BigInteger.valueOf(groups));
    }
}
