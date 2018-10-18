package com.urise.webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsTraining {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите количество значений массива");
        int[] source = randomArray(Integer.parseInt(reader.readLine()));
        Integer[] source2 = Arrays.stream(source).boxed().toArray(Integer[]::new);
        for (int i : source) {
            System.out.println("Input element: " + i);
        }
        int result = minValue(source);
        System.out.println(result);
        List<Integer> magic = oddOrEven(Arrays.asList(source2));
        System.out.println(magic);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        AtomicInteger sum = new AtomicInteger();
        Map<Boolean, List<Integer>> someMap = integers.stream().peek(sum::addAndGet)
                .collect(Collectors.partitioningBy((x) -> x % 2 == 0));
        System.out.println(sum.get());
        return someMap.get(sum.get() % 2 == 0);
    }

    private static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce((x, y) -> (x * 10 + y)).getAsInt();
    }

    private static int[] randomArray(int num) throws IOException {
        if (num < 0) {
            throw new IOException("Отрицательное значение");
        }
        return Arrays.stream(new int[num]).map(x -> x = ((int) (Math.random() * 9) + 1)).toArray();
    }
}
