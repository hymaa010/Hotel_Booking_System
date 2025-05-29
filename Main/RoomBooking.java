import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class RoomBooking extends Booking implements Comparable<RoomBooking> {
    private final int companions;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    public static List<RoomBooking> roomBookings = new ArrayList<>();

    public RoomBooking(Guest guest, int companions, Room room, LocalDate checkIn, LocalDate checkOut) {
        super(guest, room);
        this.companions = companions;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        roomBookings.add(this);
    }

    @Override
    public long getDuration() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    @Override
    public double getTotalPrice() {
        return bookable.calculatePrice(this);
    }
    @Override
    protected void printDetails() {
        System.out.println("Guest Name: " + guest.getName());
        System.out.println("Phone Number: " + guest.getPhoneNumber());
        System.out.println("Room Type: " + bookable.getType());
        System.out.println("Companions: " + companions);
        System.out.println("Check-in Date: " + checkIn);
        System.out.println("Check-out Date: " + checkOut);
        System.out.println("Duration: " + getDuration() + " days");
        System.out.println("Price: $" + getTotalPrice());
    }

    @Override
    public int compareTo(RoomBooking other) {
        return this.checkIn.compareTo(other.checkIn);
    }

    public static List<RoomBooking> getRoomBookings() {
        return roomBookings;
    }
}