package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(20)
public class Day20 implements Day {
    private static class Simulation {
        private static class Vector {
            int x;
            int y;
            int z;

            private Vector(String input) {
                String[] split = input.substring(3, input.length() - 1).split(",");
                this.x = Integer.parseInt(split[0]);
                this.y = Integer.parseInt(split[1]);
                this.z = Integer.parseInt(split[2]);
            }

            private void update(Vector vec) {
                this.x += vec.x;
                this.y += vec.y;
                this.z += vec.z;
            }

            private int distFromZero() {
                return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
            }
        }

        private static class Particle {
            int id;
            Vector position;
            Vector velocity;
            Vector acceleration;

            private Particle(int id, String input) {
                this.id = id;
                String[] split = input.split(", ");
                this.position = new Vector(split[0]);
                this.velocity = new Vector(split[1]);
                this.acceleration = new Vector(split[2]);
            }

            private void step() {
                this.velocity.update(this.acceleration);
                this.position.update(this.velocity);
            }

            private int distFromZero() {
                return this.position.distFromZero();
            }
        }

        private static class ParticleComparator implements Comparator<Particle> {
            @Override
            public int compare(Particle a, Particle b) {
                return a.distFromZero() - b.distFromZero();
            }
        }

        List<Particle> particles;

        private Simulation(String input) {
            List<String> lines = input.lines().toList();
            List<Particle> particles = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                particles.add(new Particle(i, lines.get(i)));
            }
            this.particles = particles;
        }

        private void simulate(int time, boolean collide) {
            for (int i = 0; i < time; i++) {
                this.particles.forEach(p -> p.step());
                if (collide) {
                    this.collideUpdate();
                }
            }
        }

        private void collideUpdate() {
            Set<Integer> drop = new HashSet<>();
            for (int i = 0; i < this.particles.size(); i++) {
                Vector a = this.particles.get(i).position;
                for (int j = i + 1; j < this.particles.size(); j++) {
                    Vector b = this.particles.get(j).position;
                    if (a.x == b.x && a.y == b.y && a.z == b.z) {
                        drop.add(i);
                        drop.add(j);
                    }
                }
            }
            List<Particle> updated = new ArrayList<>();
            for (int i = 0; i < this.particles.size(); i++) {
                if (drop.contains(i)) {
                    continue;
                }
                updated.add(this.particles.get(i));
            }
            this.particles = updated;
        }
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        Simulation sim = new Simulation(input);
        sim.simulate(10000, false);
        Collections.sort(sim.particles, new Simulation.ParticleComparator());
        return Optional.of(BigInteger.valueOf(sim.particles.get(0).id));
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        Simulation sim = new Simulation(input);
        sim.simulate(10000, true);
        return Optional.of(BigInteger.valueOf(sim.particles.size()));
    }
}
