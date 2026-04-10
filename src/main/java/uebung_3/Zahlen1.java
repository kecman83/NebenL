package uebung_3;

import java.util.ArrayList;
import java.util.List;

public class Zahlen1 {
    public static void main(String[] args) throws InterruptedException {
        List<String> buchstaben = new ArrayList<>();
        buchstaben.add("a");
        buchstaben.add("b");
        buchstaben.add("c");
        buchstaben.add("d");
        buchstaben.add("e");
        buchstaben.add("f");
        buchstaben.add("g");
        buchstaben.add("h");
        buchstaben.add("i");
        buchstaben.add("j");

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("t1: " + i);
                Thread.yield(); // gibt anderen Threads die Chance
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("t2: " + buchstaben.get(i));
                Thread.yield(); // gibt anderen Threads die Chance
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Priorität setzen
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}