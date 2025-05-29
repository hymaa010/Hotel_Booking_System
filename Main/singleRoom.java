class SingleRoom extends Room {
    private static double singlePrice;
    static{
        singlePrice = 100;
    }
    public SingleRoom(){
        super(singlePrice);
        Room.setSingleRoom(Room.getSingleRoom() - 1);
    }
    @Override
    public void addRoom(){
        Room.setSingleRoom(Room.getSingleRoom() + 1);
    }
    @Override
    public String getType(){
        return "Single";
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
        SingleRoom.singlePrice= price;
    }
}