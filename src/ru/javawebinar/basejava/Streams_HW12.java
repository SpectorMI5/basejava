package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Streams_HW12 {
    private static int multiplier = 1;
    private static int result;

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
        List<Integer> numerals = new ArrayList<>();
        IntStream.of(values)
                .distinct()
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(Streams_HW12::count);
        return result;
    }

    private static void count(int x) {
        result = result + x * multiplier;
        multiplier = multiplier * 10;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = IntStream.of(integers.stream().mapToInt(i -> i).toArray())
                .sum();
        if (sum % 2 == 0) {
            return integers.stream()
                    .filter(x -> x % 2 == 1)
                    .collect(Collectors.toList());
        } else {
            return integers.stream()
                    .filter(x -> x % 2 == 0)
                    .collect(Collectors.toList());
        }
    }
}