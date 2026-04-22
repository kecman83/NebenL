package uebung_7;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SquareSumTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10_000;

    private final List<Long> numbers;
    private final int start;
    private final int end;

    public SquareSumTask(List<Long> numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;

        // Basisfall: direkt berechnen
        if (length <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                long n = numbers.get(i);
                sum += n * n;
            }
            return sum;
        }

        // Aufteilen
        int mid = start + length / 2;

        SquareSumTask left = new SquareSumTask(numbers, start, mid);
        SquareSumTask right = new SquareSumTask(numbers, mid, end);

        left.fork();              // linken Task parallel starten
        long rightResult = right.compute(); // rechten direkt berechnen
        long leftResult = left.join();      // Ergebnis warten

        return leftResult + rightResult;
    }
}