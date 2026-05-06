package Hashing;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class ParallelHashFinder {
    public static boolean findHash(String blockData, String difficulty) {
        AtomicBoolean isFound = new AtomicBoolean(false);
        AtomicLong foundNonce = new AtomicLong(-1);

        LongStream.iterate(300, n -> n + 1)  // ✅ kein Buffering wie rangeClosed
                .parallel()
                .forEach(nonce -> {
                    if (isFound.get()) return;  // früh abbrechen

                    String strToHash = blockData + nonce;
                    String hash = ParallelHashFinder.sha256(strToHash);

                    System.out.printf("\r %4d - %s - %s ",
                            nonce, Thread.currentThread().getName(), hash);

                    if (hash.startsWith(difficulty)) {
                        if (isFound.compareAndSet(false, true)) {  // ✅ nur einmal setzen
                            foundNonce.set(nonce);
                            System.out.printf("%n Gesuchter Hash mit %d Versuchen gefunden%n", nonce);
                        }
                    }
                });

        return isFound.get();
    }

    public static String sha256(String strToHash) {

        byte[] bytesOfMessage;
        try {
            bytesOfMessage = strToHash.getBytes("UTF-8");
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");

            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < thedigest.length; i++) {
                String hex = Integer.toHexString(0xff & thedigest[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String blockData = "Header:Transaktion1:Transaktion2:Transaktion3";

        ParallelHashFinder.findHash(blockData, "00000");
    }
}
