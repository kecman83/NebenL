package uebung_5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorCallable {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<Integer>> futures = new ArrayList<>();
        Random random = new Random();

        // 10 Callable-Tasks erstellen und absenden
        for (int i = 1; i <= 10; i++) {
            Callable<Integer> task = () -> {
                int zahl = random.nextInt(100); // Zufallszahl 0–99
                System.out.println("Berechnet: " + zahl + " in " + Thread.currentThread().getName());
                Thread.sleep(1000);
                return zahl;
            };

            futures.add(executor.submit(task)); // submit statt execute!
        }

        // Ergebnisse holen und aufsummieren
        int summe = 0;

        for (Future<Integer> future : futures) {
            try {
                summe += future.get(); // wartet auf Ergebnis
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Gesamtsumme: " + summe);

        executor.shutdown();
    }
}