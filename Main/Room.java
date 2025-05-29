public abstract class Room implements Bookable{
    private static int singleRoom = 3;
    private static int doubleRoom = 2;
    private static int suiteRoom = 1;
    private final double pricePerNight;


    public Room(double pricePerNight){
        this.pricePerNight = pricePerNight;
    }

    public abstract void addRoom();


    public double getPricePerNight() {
        return pricePerNight;
    }

    public static int getSingleRoom() { return singleRoom; }
    public static void setSingleRoom(int val) { singleRoom = val; }
    public static int getDoubleRoom() { return doubleRoom; }
    public static void setDoubleRoom(int val) { doubleRoom = val; }
    public static int getSuiteRoom() { return suiteRoom; }
    public static void setSuiteRoom(int val) { suiteRoom = val; }
}