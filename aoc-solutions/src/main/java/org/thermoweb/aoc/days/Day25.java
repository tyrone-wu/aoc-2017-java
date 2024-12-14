package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(25)
public class Day25 implements Day {
    private static class Transition {
        boolean writeOne;
        boolean moveLeft;
        char next;

        private Transition(String input) {
            String[] split = input.split("\\s+");
            this.writeOne = split[5].equals("1.");
            this.moveLeft = split[12].equals("left.");
            this.next = split[17].charAt(0);
        }
    }

    private static class State {
        char id;
        Transition zero;
        Transition one;

        private State(String input) {
            String[] split = input.split(":");
            this.id = split[0].charAt(split[0].length() - 1);
            this.zero = new Transition(split[2]);
            this.one = new Transition(split[3]);
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        String[] split = input.split("\n\n");
        int steps = Integer.parseInt(split[0].split("\\s+")[9]);
        Map<Character, State> states = new HashMap<>();
        for (int i = 1; i < split.length; i++) {
            State s = new State(split[i]);
            states.put(s.id, s);
        }
        char state = 'A';
        int cursor = 0;
        Map<Integer, Boolean> checksum = new HashMap<>();
        for (int i = 0; i < steps; i++) {
            State s = states.get(state);
            boolean cursorVal = checksum.getOrDefault(cursor, false);
            Transition t = cursorVal ? s.one : s.zero;
            cursorVal = t.writeOne;
            if (!cursorVal) {
                checksum.remove(cursor);
            } else {
                checksum.put(cursor, cursorVal);
            }
            if (t.moveLeft) {
                cursor--;
            } else {
                cursor++;
            }
            state = t.next;
        }
        return Optional.of(BigInteger.valueOf(checksum.size()));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        return Optional.empty();
    }
}
