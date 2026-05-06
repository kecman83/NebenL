package Rainbow;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;

public class MiniRainbowTable {
    public static List<Character> alphabet = List.of('A', 'B', 'C', 'D', 'E', 'F');
    public static ConcurrentHashMap<String, String> rainbowTable = new ConcurrentHashMap<>();

    // Rekursiv alle Kombinationen generieren und direkt hashen → in Map speichern
    public static void generateRainbowTable(String curPass, int rest) {
        if (rest == 0) {
            rainbowTable.put(sha256(curPass), curPass); // Hash → Passwort
            return;
        }
        for (Character c : alphabet) {
            generateRainbowTable(curPass + c, rest - 1);
        }
    }

    public static void generateRainbowTablePool(String curPass, int rest, int maxLength, ExecutorService pool, CountDownLatch latch) {
        if (rest == 0) {
            rainbowTable.put(sha256(curPass), curPass);
            latch.countDown();
            return;
        }
        for (Character c : alphabet) {
            final String nextPass = curPass + c;
            if (rest == maxLength) {
                pool.submit(() -> generateRainbowTablePool(nextPass, rest - 1, maxLength, pool, latch));
            } else {
                generateRainbowTablePool(nextPass, rest - 1, maxLength, pool, latch);
            }
        }
    }

    public static String sha256(String strToHash) {
        try {
            byte[] bytesOfMessage = strToHash.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] thedigest = md.digest(bytesOfMessage);

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < thedigest.length; i++) {
                String hex = Integer.toHexString(0xff & thedigest[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return
     */
    public String hashing() {
        generateRainbowTable("",8);
        List<String> keys = new ArrayList<>(rainbowTable.keySet());
        String randomKey = keys.get(new Random().nextInt(keys.size()));

        return randomKey;
    }

    /**
     *
     * @param hash
     * @return String die als value in rainbowTable ist
     */
    public String krack(String hash){
        return rainbowTable.get(hash);
    }
    public static void main(String[] args) throws InterruptedException {
        int laenge = 8;

        System.out.println("Generiere Rainbow Table...");
        long start = System.currentTimeMillis();

        generateRainbowTable("", laenge);

        long end = System.currentTimeMillis();
        System.out.println("Fertig! Einträge: " + rainbowTable.size());
        System.out.println(rainbowTable);
        System.out.println("Zeit: " + (end - start) + " ms");


        int taskCount = (int) Math.pow(alphabet.size(), laenge);
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CountDownLatch latch = new CountDownLatch(taskCount);

        long start2 = System.currentTimeMillis();

        generateRainbowTablePool("", laenge, laenge, pool, latch);
        latch.await();

        long end2 = System.currentTimeMillis();
        System.out.println("Zeit: " + (end2 - start2) + " ms");

        pool.shutdown();

    }
}