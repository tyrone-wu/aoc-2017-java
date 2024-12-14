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

@DaySolver(23)
public class Day23 implements Day {
    private static class Cpu {
        private enum Op {
            SET, SUB, MUL, JNZ,
        }

        private static class Instruction {
            Op op;
            Pair<Character, Long> x;
            Pair<Character, Long> y;


            private Instruction(String input) {
                String[] split = input.split("\\s+");
                this.op = switch (split[0]) {
                    case "set" -> Op.SET;
                    case "sub" -> Op.SUB;
                    case "mul" -> Op.MUL;
                    case "jnz" -> Op.JNZ;
                    default -> throw new RuntimeException("oh no");
                };
                try {
                    this.x = Pair.of(null, Long.parseLong(split[1]));
                } catch (NumberFormatException e) {
                    char c = split[1].charAt(0);
                    this.x = Pair.of(c, null);
                }
                if (split.length > 2) {
                    try {
                        this.y = Pair.of(null, Long.parseLong(split[2]));
                    } catch (NumberFormatException e) {
                        char c = split[2].charAt(0);
                        this.y = Pair.of(c, null);
                    }
                } else {
                    this.y = null;
                }
            }
        }

        long[] registers;
        List<Instruction> instructions;
        int i;

        private Cpu(String input) {
            this.registers = new long[8];
            this.instructions = new ArrayList<>();
            input.lines().forEach(line -> {
                this.instructions.add(new Instruction(line));
            });
            this.i = 0;
        }

        private long getInt(Pair<Character, Long> union) {
            if (union.getLeft() == null) {
                return union.getRight();
            } else {
                return this.registers[union.getLeft() - 'a'];
            }
        }

        private boolean runInstruction() {
            boolean ranMul = false;
            Instruction insn = this.instructions.get(this.i);
            switch (insn.op) {
                case SET -> {
                    char reg = insn.x.getLeft();
                    this.registers[reg - 'a'] = this.getInt(insn.y);
                }
                case SUB -> {
                    char reg = insn.x.getLeft();
                    this.registers[reg - 'a'] = this.registers[reg - 'a'] - this.getInt(insn.y);
                }
                case MUL -> {
                    char reg = insn.x.getLeft();
                    this.registers[reg - 'a'] = this.registers[reg - 'a'] * this.getInt(insn.y);
                    ranMul = true;
                }
                case JNZ -> {
                    if (this.getInt(insn.x) != 0) {
                        this.i += this.getInt(insn.y) - 1;
                    }
                }
            }
            this.i++;
            return ranMul;
        }

        // private void set(char reg, long num) {
        //     this.registers[reg - 'a'] = num;
        // }

        // private long get(char reg) {
        //     return this.registers[reg - 'a'];
        // }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Cpu cpu = new Cpu(input);
        int mul = 0;
        while (cpu.i < cpu.instructions.size()) {
            if (cpu.runInstruction()) {
                mul++;
            }
        }
        return Optional.of(BigInteger.valueOf(mul));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        // Cpu cpu = new Cpu(input);
        // cpu.registers[0] = 1L;
        // while (cpu.i < cpu.instructions.size()) {
        //     cpu.runInstruction();
        // }
        // cpu.set('b', 67 * 100 + 100000);
        // cpu.set('c', cpu.get('b') + 17000);
        // while (true) {
        //     cpu.set('f', 1);
        //     cpu.set('d', 2);
        //     while (true) {
        //         cpu.set('e', 2);
        //         while (true) {
        //             cpu.set('g', cpu.get('d') * cpu.get('e') - cpu.get('b'));
        //             if (cpu.get('g') == 0) {
        //                 cpu.set('f', 0);
        //             }
        //             cpu.set('e', cpu.get('e') + 1);
        //             cpu.set('g', cpu.get('e') - cpu.get('b'));
        //             if (cpu.get('g') == 0) {
        //                 break;
        //             }
        //         }
        //         cpu.set('d', cpu.get('d') + 1);
        //         cpu.set('g', cpu.get('d') - cpu.get('b'));
        //         if (cpu.get('g') == 0) {
        //             break;
        //         }
        //     }
        //     if (cpu.get('f') == 0) {
        //         cpu.set('h', cpu.get('h') + 1);
        //     }
        //     cpu.set('g', cpu.get('b') - cpu.get('c'));
        //     if (cpu.get('g') == 0) {
        //         break;
        //     }
        //     cpu.set('b', cpu.get('b') + 17);
        // }
        int h = 0;
        int b = 67 * 100 + 100000;
        int c = 67 * 100 + 100000 + 17000;
        // long g = 0;
        // while (true) {
        //     long f = 1;
        //     long d = 2;
        //     while (true) {
        //         long e = 2;
        //         while (true) {
        //             g = d * e - b;
        //             if (g == 0) {
        //                 f = 0;
        //             }
        //             e++;
        //             g = e - b;
        //             if (g == 0) {
        //                 break;
        //             }
        //         }
        //         d++;
        //         g = d - b;
        //         if (g == 0) {
        //             break;
        //         }
        //     }
        //     if (f == 0) {
        //         h++;
        //     }
        //     g = b - c;
        //     if (g == 0) {
        //         break;
        //     }
        //     b += 17;
        // }
        for (int g = b; g <= c; g += 17) {
            int f = 1;
            for (int d = 2; d <= g; d++) {
                for (int e = 2; e <= g; e++) {
                    if (d * e > g) {
                        break;
                    } else if (d * e == g) {
                        f = 0;
                        break;
                    }
                }
            }
            if (f == 0) {
                h++;
            }
        }
        return Optional.of(BigInteger.valueOf(h));
    }
}
