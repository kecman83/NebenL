package uebung_6;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.incrementAndGet();
    }

    public int get() {
        return value.get();
    }
}
