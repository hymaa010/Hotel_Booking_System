public abstract class Hall implements Bookable {
    private static int weddingHall = 1;
    private static int conferenceHall = 1;
    private final double pricePerHour;

    public Hall(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public abstract void addHall();

    public abstract String getType();

    public double getPricePerHour() {
        return pricePerHour;
    }

    public static int getWeddingHall() { return weddingHall; }
    public static void setWeddingHall(int val) { weddingHall = val; }
    public static int getConferenceHall() { return conferenceHall; }
    public static void setConferenceHall(int val) { conferenceHall = val; }
}
