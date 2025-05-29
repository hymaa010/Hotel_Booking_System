public class WeddingHall extends Hall {
    private static double weddingPrice;
    static {
        weddingPrice = 500;
    }

    public WeddingHall() {
        super(weddingPrice);
        Hall.setWeddingHall(Hall.getWeddingHall() - 1);
    }

    @Override
    public void addHall() {
        Hall.setWeddingHall(Hall.getWeddingHall() + 1);
    }

    @Override
    public String getType() {
        return "Wedding Hall";
    }

    @Override
    public double calculatePrice(Booking booking) {
        return getPricePerHour() * booking.getDuration();
    }

    @Override
    public double applyPromoCode(String code, Booking booking) {
        return booking.applyPromoCode(code);
    }

    public void setPrice(double price) {
        WeddingHall.weddingPrice = price;
    }
}
