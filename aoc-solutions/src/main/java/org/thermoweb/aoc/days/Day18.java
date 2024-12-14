package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(18)
public class Day18 implements Day {
    private enum Op {
        SND, SET, ADD, MUL, MOD, RCV, JGZ,
    }

    private static class Program {
        private static class Instruction {
            private Op op;
            private Pair<Character, Long> x;
            private Pair<Character, Long> y;

            private Instruction(String input, Map<Character, Long> registers) {
                String[] split = input.split("\\s+");
                this.op = switch (split[0]) {
                    case "snd" -> Op.SND;
                    case "set" -> Op.SET;
                    case "add" -> Op.ADD;
                    case "mul" -> Op.MUL;
                    case "mod" -> Op.MOD;
                    case "rcv" -> Op.RCV;
                    case "jgz" -> Op.JGZ;
                    default -> throw new RuntimeException("oh no");
                };
                try {
                    this.x = Pair.of(null, Long.parseLong(split[1]));
                } catch (NumberFormatException e) {
                    char c = split[1].charAt(0);
                    this.x = Pair.of(c, null);
                    registers.put(c, 0L);
                }
                if (split.length > 2) {
                    try {
                        this.y = Pair.of(null, Long.parseLong(split[2]));
                    } catch (NumberFormatException e) {
                        char c = split[2].charAt(0);
                        this.y = Pair.of(c, null);
                        registers.put(c, 0L);
                    }
                } else {
                    this.y = null;
                }
            }
        }

        private Map<Character, Long> registers;
        private List<Instruction> instructions;
        private Pair<Long, Boolean> rcvSnd;
        private int i;
        private Queue<Long> rcv;
        private boolean waiting;
        private long sndCount;

        private Program(String input) {
            this.registers = new HashMap<>();
            this.instructions = new ArrayList<>();
            this.rcvSnd = Pair.of(0L, false);
            this.i = 0;
            this.rcv = new LinkedList<>();
            this.waiting = false;
            this.sndCount = 0;
            input.lines().forEach(line -> {
                this.instructions.add(new Instruction(line, registers));
            });
        }

        private long getInt(Pair<Character, Long> union) {
            if (union.getLeft() == null) {
                return union.getRight();
            } else {
                return this.registers.get(union.getLeft());
            }
        }

        private void runInstruction() {
            Instruction insn = this.instructions.get(this.i);
            switch (insn.op) {
                case SND -> {
                    this.rcvSnd = Pair.of(this.getInt(insn.x), false);
                }
                case SET -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.getInt(insn.y));
                }
                case ADD -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.registers.get(reg) + this.getInt(insn.y));
                }
                case MUL -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.registers.get(reg) * this.getInt(insn.y));
                }
                case MOD -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.registers.get(reg) % this.getInt(insn.y));
                }
                case RCV -> {
                    if (this.getInt(insn.x) != 0) {
                        this.rcvSnd = Pair.of(this.rcvSnd.getLeft(), true);
                    }
                }
                case JGZ -> {
                    if (this.getInt(insn.x) > 0) {
                        this.i += this.getInt(insn.y) - 1;
                    }
                }
            }
        }

        private void runInstructionP2(Program other) {
            Instruction insn = this.instructions.get(this.i);
            switch (insn.op) {
                case SND -> {
                    other.rcv.add(this.getInt(insn.x));
                    other.waiting = false;
                    this.sndCount++;
                }
                case SET -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.getInt(insn.y));
                }
                case ADD -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.registers.get(reg) + this.getInt(insn.y));
                }
                case MUL -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.registers.get(reg) * this.getInt(insn.y));
                }
                case MOD -> {
                    char reg = insn.x.getLeft();
                    this.registers.put(reg, this.registers.get(reg) % this.getInt(insn.y));
                }
                case RCV -> {
                    if (this.rcv.isEmpty()) {
                        this.waiting = true;
                        this.i--;
                    } else {
                        char reg = insn.x.getLeft();
                        this.registers.put(reg, this.rcv.remove());
                    }
                }
                case JGZ -> {
                    if (this.getInt(insn.x) > 0) {
                        this.i += this.getInt(insn.y) - 1;
                    }
                }
            }
        }

        private OptionalLong getRcvSnd() {
            if (this.rcvSnd.getRight()) {
                return OptionalLong.of(this.rcvSnd.getLeft());
            } else {
                return OptionalLong.empty();
            }
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Program prog = new Program(input);
        while (prog.i < prog.instructions.size()) {
            prog.runInstruction();
            prog.i++;
            if (prog.getRcvSnd().isPresent()) {
                break;
            }
        }
        return Optional.of(BigInteger.valueOf(prog.getRcvSnd().getAsLong()));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Program prog0 = new Program(input);
        prog0.registers.put('p', 0L);
        Program prog1 = new Program(input);
        prog1.registers.put('p', 1L);
        while (!(prog0.waiting && prog1.waiting)) {
            while (!prog0.waiting) {
                prog0.runInstructionP2(prog1);
                prog0.i++;
            }
            while (!prog1.waiting) {
                prog1.runInstructionP2(prog0);
                prog1.i++;
            }
        }
        return Optional.of(BigInteger.valueOf(prog1.sndCount));
    }
}
