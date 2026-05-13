package Bahnhof;

import java.util.concurrent.Semaphore;

public class Bahnhof {
    static Semaphore semaphore = new Semaphore(3);

    public void ankunft(Train train) {
        new Thread(() -> {
            try {
                System.out.println("Train " + train.getTrainID() + " kommt an");

                if (semaphore.availablePermits() == 0) {
                    System.out.println("Train " + train.getTrainID() + " wartet vor dem Bahnhof");
                }

                semaphore.acquire(); // wartet hier bis Platz frei wird

                System.out.println("Train " + train.getTrainID() + " fährt in den Bahnhof ein");

                Thread.sleep(train.getAufenhalt());

                System.out.println("Train " + train.getTrainID() + " verlässt den Bahnhof");

                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
