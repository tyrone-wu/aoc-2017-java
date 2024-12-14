package org.thermoweb.aoc.days;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.thermoweb.aoc.Day;
import org.thermoweb.aoc.DaySolver;

@DaySolver(10)
public class Day10 implements Day {
    private static int[] parseInput(String input) {
        String[] split = input.split(",");
        int[] lengths = new int[split.length];
        for (int i = 0; i < lengths.length; i++) {
            lengths[i] = Integer.parseInt(split[i]);
        }
        return lengths;
    }

    private static Pair<Integer, Integer> knotHash(int[] nums, int curPos, int skipSize, int len) {
        int left = curPos;
        int right = curPos + len - 1;
        while (left < right) {
            int leftIdx = left % nums.length;
            int rightIdx = right % nums.length;
            int tmp = nums[leftIdx];
            nums[leftIdx] = nums[rightIdx];
            nums[rightIdx] = tmp;
            left++;
            right--;
        }

        curPos = (curPos + len + skipSize) % nums.length;
        skipSize++;
        return Pair.of(curPos, skipSize);
    }

    @Override
    public Optional<BigInteger> partOne(String input) {
        int[] lengths = parseInput(input);
        int[] nums = new int[256];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        int curPos = 0;
        int skipSize = 0;
        for (var len : lengths) {
            Pair<Integer, Integer> stuff = knotHash(nums, curPos, skipSize, len);
            curPos = stuff.getLeft();
            skipSize = stuff.getRight();
        }
        return Optional.of(BigInteger.valueOf(nums[0] * nums[1]));
    }

    private static List<Byte> parseInputP2(String input) {
        List<Byte> bytes = input.chars().mapToObj(b -> (byte) b).collect(Collectors.toList());
        bytes.addAll(Arrays.stream(new int[]{17, 31, 73, 47, 23}).mapToObj(b -> (byte) b).toList());
        return bytes;
    }

    public static BigInteger finalHash(String input) {
        List<Byte> lengths = parseInputP2(input);
        int[] nums = new int[256];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        int curPos = 0;
        int skipSize = 0;
        for (int i = 0; i < 64; i++) {
            for (var len : lengths) {
                Pair<Integer, Integer> stuff = knotHash(nums, curPos, skipSize, len);
                curPos = stuff.getLeft();
                skipSize = stuff.getRight();
            }
        }
        int[] denseHash = new int[nums.length / 16];
        for (int i = 0; i < nums.length; i++) {
            denseHash[i / 16] ^= nums[i];
        }
        BigInteger hash = BigInteger.valueOf(0);
        for (var hex : denseHash) {
            hash = hash.shiftLeft(8).or(BigInteger.valueOf(hex));
        }
        return hash;
    }

    @Override
    public Optional<BigInteger> partTwo(String input) {
        System.out.println(finalHash(input).toString(16));
        return Optional.empty();
    }
}
