package uebung_7;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ParallelSum {

    public static void main(String[] args) {

        List<Long> numbers = LongStream.range(0, 1_000_000)
                .boxed()
                .collect(Collectors.toList());

        long start = System.nanoTime();

        long sum = numbers.parallelStream()
                .mapToLong(n -> n * n)
                .sum();

        long end = System.nanoTime();

        System.out.println("Parallele Summe: " + sum);
        System.out.println("Zeit: " + formatTime(end - start));
    }

    private static String formatTime(long nanos) {
        if (nanos >= 1_000_000_000) {
            return String.format("%.3f s", nanos / 1_000_000_000.0);
        } else if (nanos >= 1_000_000) {
            return String.format("%.3f ms", nanos / 1_000_000.0);
        } else {
            return nanos + " ns";
        }
    }
}