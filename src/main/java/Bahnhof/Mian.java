package Bahnhof;

import java.util.ArrayList;
import java.util.List;

public class Mian {
    private static List<Train> trains = new ArrayList<>();
    public static void main(String[] args)
    {
        getTrains();
        Bahnhof bahnhof = new Bahnhof();
        for(Train train : trains)
        {
            bahnhof.ankunft(train);
        }
    }
    private static List<Train>  getTrains()
    {
        Train t1 = new Train(1, 1000, 2000);
        Train t2 = new Train(2,100,250);
        Train t3 = new Train(3,200,300);
        Train t4 = new Train(4,300,400);
        Train t5 = new Train(5,400,500);
        trains.add(t1);
        trains.add(t2);
        trains.add(t3);
        trains.add(t4);
        trains.add(t5);
        return trains;
    }
}
