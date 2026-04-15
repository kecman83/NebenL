package uebung_5;

import java.util.concurrent.locks.ReentrantLock;

public class SharedObjectWithLock {

    // The lock and counter should be instance variables
    // if multiple threads share one instance of this class.
    private final ReentrantLock lock = new ReentrantLock();
    private int counter = 0;

    public static void main(String[] args) {
        SharedObjectWithLock example = new SharedObjectWithLock();
        example.perform();
        System.out.println("Counter value: " + example.getCounter());
    }

    public void perform() {
        lock.lock(); // Acquire the lock
        try {
            // Critical section: only one thread can be here at a time
            counter++;
        } finally {
            // Always unlock in a finally block to prevent deadlocks
            // if an exception occurs during the increment.
            lock.unlock();
        }
    }

    public int getCounter() {
        return counter;
    }
}