package Bahnhof;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MitExecut {

    private final Semaphore semaphore = new Semaphore(3);
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void tuTU(Train train) {
        executor.execute(() -> {
            try {
                System.out.println("Train " + train.getTrainID() + " kommt an");

                if (!semaphore.tryAcquire()) {
                    System.out.println("Train " + train.getTrainID() + " wartet vor dem Bahnhof");
                    semaphore.acquire();
                }

                System.out.println("Train " + train.getTrainID() + " fährt in den Bahnhof ein");

                Thread.sleep(train.getAufenhalt());

                System.out.println("Train " + train.getTrainID() + " verlässt den Bahnhof");

                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}