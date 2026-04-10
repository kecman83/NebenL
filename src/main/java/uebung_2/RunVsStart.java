package uebung_2;


public class RunVsStart {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("Im Thread: " + Thread.currentThread().getName());
        });

        // Falsch: läuft im main-Thread, keine echte Nebenläufigkeit
        t.run();

        // Richtig: jetzt wirklich neuer Thread
        t.start();
    }
}

