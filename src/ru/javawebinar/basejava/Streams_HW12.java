package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Streams_HW12 {

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        System.out.println("1. Результат = " + minValue(values));

        List<Integer> integers = new ArrayList<>();
        for (int value : values) {
            integers.add(value);
        }
        System.out.println("2. Результат = " + oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, x) -> acc * 10 + x);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = IntStream.of(integers.stream().mapToInt(i -> i).toArray())
                .sum();
        return integers.stream()
                .filter(x -> sum % 2 != x % 2)
                .collect(Collectors.toList());
    }
}