package uebung_2;

public class MyThread extends Thread {

    @Override
    public void run() {
        // Code, der im neuen Thread läuft
        for (int i = 1; i <= 5; i++) {
            System.out.println("MyThread: " + i);
            try {
                Thread.sleep(500);  // 0,5 s Pause
            } catch (InterruptedException e) {
                System.out.println("MyThread wurde unterbrochen");
            }
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();  // Thread-Objekt erstellen
        t.start();                    // neuen Thread starten

        // Hauptthread läuft parallel weiter
        for (int i = 1; i <= 5; i++) {
            System.out.println("main: " + i);
        }
    }
}

