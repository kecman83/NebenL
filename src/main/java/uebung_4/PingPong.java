package uebung_4;

public class PingPong {

    private static final Object lock = new Object();
    private static boolean pingTurn = true; // Ping beginnt

    public static void main(String[] args) throws InterruptedException {

        Thread ping = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    while (!pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("Ping");
                    pingTurn = false;
                    lock.notifyAll();
                }
            }
        });

        Thread pong = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    while (pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("Pong");
                    pingTurn = true;
                    lock.notifyAll();
                }
            }
        });

        ping.start();
        pong.start();

        ping.join();
        pong.join();
    }
}