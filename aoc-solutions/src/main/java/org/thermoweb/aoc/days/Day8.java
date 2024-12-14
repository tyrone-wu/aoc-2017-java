package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(8)
public class Day8 implements Day {
    private static class Cpu {
        private static class Instruction {
            private enum Op {
                EQ, NEQ, G, L, GEQ, LEQ
            }

            String reg;
            boolean inc;
            int val;
            String condReg;
            Op op;
            int condVal;

            public Instruction(String input, Map<String, Integer> regs) {
                String[] split = input.split("\\s+");
                this.reg = split[0];
                this.inc = split[1].equals("inc");
                this.val = Integer.parseInt(split[2]);
                this.condReg = split[4];
                this.op = switch (split[5]) {
                    case "==" -> Op.EQ;
                    case "!=" -> Op.NEQ;
                    case ">" -> Op.G;
                    case "<" -> Op.L;
                    case ">=" -> Op.GEQ;
                    default -> Op.LEQ;
                };
                this.condVal = Integer.parseInt(split[6]);

                regs.put(this.reg, 0);
                regs.put(this.condReg, 0);
            }
        }

        Instruction[] insns;
        Map<String, Integer> regs;
        int maxVal;

        public Cpu(String input) {
            Map<String, Integer> regs = new HashMap<>();
            Instruction[] insns = input.lines().map(line -> new Instruction(line, regs)).toArray(Instruction[]::new);
            this.insns = insns;
            this.regs = regs;
            this.maxVal = 0;
        }

        private void eval(Instruction ins) {
            int condReg = this.regs.get(ins.condReg);
            boolean cond = switch (ins.op) {
                case EQ -> condReg == ins.condVal;
                case NEQ -> condReg != ins.condVal;
                case G -> condReg > ins.condVal;
                case L -> condReg < ins.condVal;
                case GEQ -> condReg >= ins.condVal;
                case LEQ -> condReg <= ins.condVal;
            };
            if (cond) {
                if (ins.inc) {
                    regs.put(ins.reg, regs.get(ins.reg) + ins.val);
                } else {
                    regs.put(ins.reg, regs.get(ins.reg) - ins.val);
                }
                this.maxVal = this.maxVal < regs.get(ins.reg) ? regs.get(ins.reg) : this.maxVal;
            }
        }

        private void run() {
            for (var i : this.insns) {
                eval(i);
            }
        }

        private int largestReg() {
            return this.regs.values().stream().mapToInt(Integer::intValue).max().getAsInt();
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Cpu cpu = new Cpu(input);
        cpu.run();
        return Optional.of(BigInteger.valueOf(cpu.largestReg()));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Cpu cpu = new Cpu(input);
        cpu.run();
        return Optional.of(BigInteger.valueOf(cpu.maxVal));
    }
}
