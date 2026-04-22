package uebung_6;

public class AtomicCounterTest {
    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();

        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (Thread t : threads) t.join();

        System.out.println("AtomicCounter result: " + counter.get());
    }
}
