package uebung_2;

public class LambdaThreadExample {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("Thread läuft mit Lambda");
        });

        t.start();
        System.out.println("main läuft weiter");
    }
}
