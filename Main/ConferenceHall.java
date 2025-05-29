public class ConferenceHall extends Hall {
    private static double conferencePrice;
    static {
        conferencePrice = 300;
    }

    public ConferenceHall() {
        super(conferencePrice);
        Hall.setConferenceHall(Hall.getConferenceHall() - 1);
    }

    @Override
    public void addHall() {
        Hall.setConferenceHall(Hall.getConferenceHall() + 1);
    }

    @Override
    public String getType() {
        return "Conference Hall";
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
        ConferenceHall.conferencePrice = price;
    }
}
