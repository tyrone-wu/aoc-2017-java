package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(21)
public class Day21 implements Day {
    private static class Image {
        boolean[][] image;

        private Image(String input) {
            String[] split = input.split("/");
            this.image = new boolean[split.length][split.length];
            for (int i = 0; i < split.length; i++) {
                char[] line = split[i].toCharArray();
                for (int j = 0; j < split.length; j++) {
                    if (line[j] == '#') {
                        this.image[i][j] = true;
                    }
                }
            }
        }

        private Image(boolean[][] image) {
            this.image = image;
        }

        private int size() {
            return this.image.length;
        }

        private Image mirror() {
            boolean[][] mirror = new boolean[this.size()][this.size()];
            for (int i = 0; i < this.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    mirror[j][this.size() - 1 - i] = this.image[j][i];
                }
            }
            return new Image(mirror);
        }

        private Image rotateCw() {
            boolean[][] cw = new boolean[this.size()][this.size()];
            for (int i = 0; i < this.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    cw[j][this.size() - 1 - i] = this.image[i][j];
                }
            }
            return new Image(cw);
        }

        private void enhance(EnhancementRules rules) {
            int size = this.size() % 2 == 0 ? 2 : 3;
            int newSize = this.size() / size * (size + 1);
            boolean[][] newImage = new boolean[newSize][newSize];
            for (int h = 0; h < this.size() / size; h++) {
                for (int w = 0; w < this.size() / size; w++) {
                    boolean[][] smol = new boolean[size][size];
                    for (int r = 0; r < size; r++) {
                        for (int c = 0; c < size; c++) {
                            int row = h * size + r;
                            int col = w * size + c;
                            if (this.image[row][col]) {
                                smol[r][c] = true;
                            }
                        }
                    }
                    Image smolImg = new Image(smol);
                    for (var rule : rules.rules) {
                        if (rule.matches(smolImg)) {
                            for (int r = 0; r < rule.to.size(); r++) {
                                for (int c = 0; c < rule.to.size(); c++) {
                                    int row = h * (size + 1) + r;
                                    int col = w * (size + 1) + c;
                                    if (rule.to.image[r][c]) {
                                        newImage[row][col] = true;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
            this.image = newImage;
        }

        private int getOn() {
            int on = 0;
            for (var row : this.image) {
                for (var b : row) {
                    if (b) {
                        on++;
                    }
                }
            }
            return on;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (var row : this.image) {
                for (var b : row) {
                    s.append(b ? '#' : '.');
                }
                s.append('\n');
            }
            return s.toString();
        }

        // @Override
        // public boolean equals(Object o) {
        //     if (!(o instanceof Image)) {
        //         return false;
        //     }
        //     Image img = (Image) o;
        //     if (this.size() != img.size()) {
        //         return false;
        //     }
        //     for (int i = 0; i < this.size(); i++) {
        //         for (int j = 0; j < this.size(); j++) {
        //             if (this.image[i][j] != img.image[i][j]) {
        //                 return false;
        //             }
        //         }
        //     }
        //     return true;
        // }
    }

    private static class EnhancementRules {
        private static class Rule {
            List<Image> from;
            Image to;

            private Rule(String input) {
                String[] split = input.split(" => ");
                Image r = new Image(split[0]);
                Image rCw = r.rotateCw();
                Image rCwCw = rCw.rotateCw();
                Image rCwCwCw = rCwCw.rotateCw();
                Image m = r.mirror();
                Image mCw = m.rotateCw();
                Image mCwCw = mCw.rotateCw();
                Image mCwCwCw = mCwCw.rotateCw();
                this.from = List.of(r, rCw, rCwCw, rCwCwCw, m, mCw, mCwCw, mCwCwCw);
                this.to = new Image(split[1]);
            }

            // private Rule(Image from, Image to) {
            //     this.from = from;
            //     this.to = to;
            // }

            // private Rule mirror() {
            //     Image fromMirror = this.from.mirror();
            //     // Image toMirror = this.to.mirror();
            //     return new Rule(fromMirror, this.to);
            // }

            // private Rule rotateCw() {
            //     Image fromCw = this.from.rotateCw();
            //     // Image toCw = this.to.rotateCw();
            //     return new Rule(fromCw, this.to);
            // }

            private boolean matches(Image img) {
                for (var r : this.from) {
                    if (r.size() != img.size()) {
                        continue;
                    }
                    boolean matches = true;
                    for (int i = 0; i < r.size(); i++) {
                        for (int j = 0; j < r.size(); j++) {
                            if (r.image[i][j] != img.image[i][j]) {
                                matches = false;
                            }
                        }
                    }
                    if (matches) {
                        return true;
                    }
                }
                return false;
            }
        }

        List<Rule> rules;

        private EnhancementRules(String input) {
            this.rules = new ArrayList<>();
            input.lines().forEach(line -> {
                Rule r = new Rule(line);
                // Rule rCw = r.rotateCw();
                // Rule rCwCw = rCw.rotateCw();
                // Rule rCwCwCw = rCwCw.rotateCw();
                this.rules.add(r);
                // this.rules.add(rCw);
                // this.rules.add(rCwCw);
                // this.rules.add(rCwCwCw);
                // Rule m = r.mirror();
                // Rule mCw = m.rotateCw();
                // Rule mCwCw = mCw.rotateCw();
                // Rule mCwCwCw = mCwCw.rotateCw();
                // this.rules.add(m);
                // this.rules.add(mCw);
                // this.rules.add(mCwCw);
                // this.rules.add(mCwCwCw);
            });
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Image img = new Image(".#./..#/###");
        EnhancementRules er = new EnhancementRules(input);
        for (int i = 0; i < 5; i++) {
            img.enhance(er);
            // System.out.println(img.toString());
        }
        return Optional.of(BigInteger.valueOf(img.getOn()));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Image img = new Image(".#./..#/###");
        EnhancementRules er = new EnhancementRules(input);
        for (int i = 0; i < 18; i++) {
            img.enhance(er);
            // System.out.println(img.toString());
        }
        return Optional.of(BigInteger.valueOf(img.getOn()));
    }
}
