package uebung_5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorRunnable {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable task = () -> {
            try {
                System.out.println("Aufgabe läuft in: " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 10 Aufgaben senden
        for (int i = 0; i < 10; i++) {
            executor.execute(task);
        }

        executor.shutdown();
    }
}