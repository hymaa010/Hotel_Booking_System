import java.util.*;

// Abstract Booking class
public abstract class Booking {
    protected Bookable bookable;
    Guest guest;
    protected static List<Booking> allBookings = new ArrayList<>();

    public Booking(Guest guest, Bookable bookable) {
        this.guest = guest;
        this.bookable = bookable;
        allBookings.add(this);
    }

    // Common methods for all booking types
    public abstract double getTotalPrice();
    public abstract long getDuration();



    // Print all bookings regardless of type
    public static void printAllBookings() {
        for (Booking booking : allBookings) {
            booking.printDetails();
            System.out.println("---------------------------------------");
        }
    }


    protected abstract void printDetails();

    // Method to apply promo code
    public double applyPromoCode(String code) {
        return (1 - Offer.getPromoValue(code)) * getTotalPrice();
    }
}