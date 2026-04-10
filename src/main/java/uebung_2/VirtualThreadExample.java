package uebung_2;

public class VirtualThreadExample {

    public static void main(String[] args) throws InterruptedException {
        // ab Java 21: Virtuelle Threads
        Thread vt = Thread.ofVirtual().start(() -> {
            System.out.println("Virtueller Thread: " + Thread.currentThread());
        });

        vt.join(); // ggf. mit try/catch für InterruptedException
    }
}
