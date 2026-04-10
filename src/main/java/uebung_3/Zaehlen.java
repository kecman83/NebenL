package uebung_3;

import java.util.ArrayList;
import java.util.List;

public class Zaehlen {
    public static void main(String[] args) throws InterruptedException {
        List buchstaben = new ArrayList();
        buchstaben.add("a");
        buchstaben.add("b");
        buchstaben.add("c");
        buchstaben.add("d");
        buchstaben.add("e");
        buchstaben.add("f");
        buchstaben.add("g");
        buchstaben.add("h");
        buchstaben.add("i");
        buchstaben.add("j");
        Thread t1 = new Thread(() ->{
            for(int i=1; i <=10;i++){
                System.out.println("t1: " + i);
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(() ->{
           for(int i=0; i <10;i++){
               System.out.println("t2: " + buchstaben.get(i));
               try{
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
