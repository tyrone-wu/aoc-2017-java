package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(22)
public class Day22 implements Day {
    private static class Cluster {
        Map<Pair<Integer, Integer>, Integer> infected;
        int x;
        int y;
        int dir;

        private Cluster(String input) {
            this.infected = new HashMap<>();
            List<String> lines = input.lines().toList();
            for (int y = 0; y < lines.size(); y++) {
                for (int x = 0; x < lines.get(0).length(); x++) {
                    if (lines.get(y).charAt(x) == '#') {
                        this.infected.put(Pair.of(x, y), 2);
                    }
                }
            }
            this.x = lines.get(0).length() / 2;
            this.y = lines.size() / 2;
            this.dir = 0;
        }

        private boolean burst() {
            boolean causedInfection = false;
            Pair<Integer, Integer> current = Pair.of(this.x, this.y);
            boolean isInfected = this.infected.containsKey(current);
            int turn = isInfected ? 1 : 3;
            this.dir = (this.dir + turn) % 4;
            if (isInfected) {
                this.infected.remove(current);
            } else {
                causedInfection = true;
                this.infected.put(current, 2);
            }
            switch (this.dir) {
                case 0 -> this.y--;
                case 1 -> this.x++;
                case 2 -> this.y++;
                case 3 -> this.x--;
                default -> throw new RuntimeException("ahhhh");
            }
            return causedInfection;
        }

        private boolean burstP2() {
            boolean causedInfection = false;
            Pair<Integer, Integer> current = Pair.of(this.x, this.y);
            int infectedStatus = this.infected.containsKey(current) ? this.infected.get(current) : 0;
            if (infectedStatus + 1 == 4) {
                this.infected.remove(current);
            } else {
                this.infected.put(current, (infectedStatus + 1) % 4);
                if (infectedStatus + 1 == 2) {
                    causedInfection = true;
                }
            }
            int turn = switch (infectedStatus) {
                case 0 -> 3;
                case 1 -> 0;
                case 2 -> 1;
                case 3 -> 2;
                default -> throw new RuntimeException("ahhhh");
            };
            this.dir = (this.dir + turn) % 4;
            switch (this.dir) {
                case 0 -> this.y--;
                case 1 -> this.x++;
                case 2 -> this.y++;
                case 3 -> this.x--;
                default -> throw new RuntimeException("ahhhh");
            }
            return causedInfection;
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Cluster cluster = new Cluster(input);
        int causedInfection = 0;
        for (int i = 0; i < 10000; i++) {
            if (cluster.burst()) {
                causedInfection++;
            }
        }
        return Optional.of(BigInteger.valueOf(causedInfection));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Cluster cluster = new Cluster(input);
        int causedInfection = 0;
        for (int i = 0; i < 10000000; i++) {
            if (cluster.burstP2()) {
                causedInfection++;
            }
        }
        return Optional.of(BigInteger.valueOf(causedInfection));
    }
}
