package uebung_2;


public class AnonymousThreadExample {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymer Runnable-Thread läuft");
            }
        });

        t.start();
        System.out.println("main läuft weiter");
    }
}
