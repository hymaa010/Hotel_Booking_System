public interface Bookable{
    double calculatePrice(Booking booking);
    String  getType();
    void setPrice(double price);
    double applyPromoCode(String code,Booking booking);
}
