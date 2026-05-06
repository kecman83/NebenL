package Rainbow;

public class HashKnak {
    public static void main(String[] args) {
        MiniRainbowTable rainbow = new MiniRainbowTable();

        String h = rainbow.hashing();
        System.out.println("Hash: " + h);
        System.out.println(rainbow.krack(h));
    }

}
