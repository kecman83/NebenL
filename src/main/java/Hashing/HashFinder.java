package Hashing;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class HashFinder {

    public static boolean findHash(String blockData, String difficulty){
        boolean isFound = false;
        // long nonce = Long.MIN_VALUE;
        long nonce = 50;

        while(!isFound && nonce <= Long.MAX_VALUE){
            String strToHash = blockData+nonce;
            String hash = HashFinder.sha256(strToHash);
            System.out.printf("\r %4d - %s - %s ",nonce,Thread.currentThread().getName(),hash);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(hash.startsWith(difficulty)){
                isFound = true;
                System.out.printf("%n Gesuchter Hash mit %d Versuchen gefunden %n",nonce+1);
            }
            nonce++;
        }

        return isFound;
    }

    public static String sha256(String strToHash) {

        byte[] bytesOfMessage;
        try {
            bytesOfMessage = strToHash.getBytes("UTF-8");
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");

            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuffer hexString = new StringBuffer();
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

        HashFinder.findHash(blockData, "0");
    }
}
