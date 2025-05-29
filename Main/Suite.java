class Suite extends Room {
    private static double suitePrice;
    static{
        suitePrice = 250;
    }
    public Suite() {
        super(suitePrice);
        Room.setSuiteRoom(Room.getSuiteRoom() - 1);
    }
    @Override
    public void addRoom(){
        Room.setSuiteRoom(Room.getSuiteRoom() + 1);
    }
    @Override
    public String getType() {
        return "Suite";
    }
    @Override
    public double calculatePrice(Booking booking){
        return getPricePerNight() * booking.getDuration();
    }
    @Override
    public double applyPromoCode(String code,Booking booking){
        return (1-Offer.getPromoValue(code))*this.calculatePrice(booking);
    }
    @Override
    public void setPrice(double price){
        Suite.suitePrice= price;
    }
}