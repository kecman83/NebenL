package Bahnhof;

public class Train extends Thread {
    private int trainID;
    private int ankunftsZ;
    private int aufenhalt;

    public Train(int trainID, int ankunftsZ, int aufenhalt)
    {
        this.trainID = trainID;
        this.ankunftsZ = ankunftsZ;
        this.aufenhalt = aufenhalt;
    }

    public int getTrainID() {
        return trainID;
    }

    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    public int getAnkunftsZ() {
        return ankunftsZ;
    }

    public void setAnkunftsZ(int ankunftsZ) {
        this.ankunftsZ = ankunftsZ;
    }

    public int getAufenhalt() {
        return aufenhalt;
    }

    public void setAufenhalt(int aufenhalt) {
        this.aufenhalt = aufenhalt;
    }
}
