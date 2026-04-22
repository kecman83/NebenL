package uebung_6;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounterMapTest {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();

            // Atomare Initialisierung pro Thread
            map.putIfAbsent(threadName, new AtomicInteger(0));

            for (int i = 0; i < 10_000; i++) {
                map.get(threadName).incrementAndGet();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task, "Thread-" + i);
            threads[i].start();
        }

        for (Thread t : threads) t.join();

        // Ausgabe der Map
        map.forEach((name, counter) ->
                System.out.println(name + " → " + counter.get())
        );
    }
}
