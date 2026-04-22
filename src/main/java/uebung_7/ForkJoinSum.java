package uebung_7;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ForkJoinSum {

    public static void main(String[] args) {

        List<Long> numbers = LongStream.range(0, 1_000_000)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();

        SquareSumTask task = new SquareSumTask(numbers, 0, numbers.size());
        long result = pool.invoke(task);

        long end = System.nanoTime();

        System.out.println("Fork/Join Summe: " + result);
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