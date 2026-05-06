package PCS;

public class ProducerConsumerRingBuffer {

    public static void main(String[] args) throws InterruptedException {
        final PC pc = new PC(2);

        Thread t1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class RingBuffer {                     // Definition einer inneren Klasse RingBuffer

        private final int[] buffer;               // Array als fester Speicher für den Puffer

        private int head = 0;                     // Index der nächsten Leseposition (Consumer liest hier)

        private int tail = 0;                     // Index der nächsten Schreibposition (Producer schreibt hier)

        private int count = 0;                    // Anzahl aktuell gespeicherter Elemente im Buffer


        public RingBuffer(int size) {             // Konstruktor: erzeugt Ringbuffer mit gegebener Größe
            buffer = new int[size];               // Array der Größe 'size' anlegen
        }


        public void put(int value) {              // Methode zum Einfügen eines Werts in den Buffer

            buffer[tail] = value;                 // Wert an aktueller Schreibposition speichern

            tail = (tail + 1) % buffer.length;    // Schreibposition um 1 erhöhen
            // % sorgt dafür, dass nach letztem Index wieder 0 kommt

            count++;                              // Anzahl gespeicherter Elemente erhöhen
        }


        public int get() {                        // Methode zum Lesen/Entfernen eines Werts

            int value = buffer[head];             // Wert an aktueller Leseposition auslesen

            head = (head + 1) % buffer.length;    // Leseposition um 1 erhöhen
            // bei Array-Ende zurück auf Anfang springen

            count--;                              // Anzahl gespeicherter Elemente reduzieren

            return value;                         // Gelesenen Wert zurückgeben
        }


        public boolean isFull() {                 // Prüft, ob Buffer voll ist
            return count == buffer.length;        // voll, wenn gespeicherte Elemente = Arraygröße
        }


        public boolean isEmpty() {                // Prüft, ob Buffer leer ist
            return count == 0;                    // leer, wenn keine Elemente gespeichert sind
        }
    }

    static class PC {
        private final RingBuffer buffer;

        public PC(int size) {
            buffer = new RingBuffer(size);
        }

        public void produce() throws InterruptedException {
            int value = 0;

            while (true) {
                synchronized (this) {
                    while (buffer.isFull()) {
                        System.out.println("Buffer voll -> Producer wartet");
                        wait();
                    }

                    buffer.put(value);
                    System.out.println("Produced: " + value);
                    value++;

                    notifyAll();
                }
                Thread.sleep(500);
            }
        }

        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (buffer.isEmpty()) {
                        System.out.println("Buffer leer -> Consumer wartet");
                        wait();
                    }

                    int val = buffer.get();
                    System.out.println("Consumed: " + val);

                    notifyAll();
                }
                Thread.sleep(500);
            }
        }
    }
}