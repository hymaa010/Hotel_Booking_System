class DoubleRoom extends Room {
    private static double doublePrice;
    static{
        doublePrice = 150;
    }
    public DoubleRoom(){
        super(doublePrice);
        Room.setDoubleRoom(Room.getDoubleRoom() - 1);
    }
    @Override
    public void addRoom(){
        Room.setDoubleRoom(Room.getDoubleRoom() + 1);
    }
    @Override
    public String getType(){
        return "Double";
    }
    @Override
    public double calculatePrice(Booking booking){
        return getPricePerNight()*booking.getDuration();
    }
    @Override
    public double applyPromoCode(String code,Booking booking){
        return (1-Offer.getPromoValue(code))*this.calculatePrice(booking);
    }
    @Override
    public void setPrice(double price){
        DoubleRoom.doublePrice= price;
    }
}