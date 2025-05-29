import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

public class HallBooking extends Booking implements Comparable<HallBooking> {
    private final int attendees;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String eventType;
    public static List<HallBooking> hallBookings = new ArrayList<>();

    public HallBooking(Guest organizer, int attendees, Hall hall, LocalDateTime startTime, LocalDateTime endTime, String eventType) {
        super(organizer, hall);
        this.attendees = attendees;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventType = eventType;
        hallBookings.add(this);
    }

    @Override
    public long getDuration() {
        return Duration.between(startTime, endTime).toHours();
    }

    @Override
    public double getTotalPrice() {
        return bookable.calculatePrice(this);
    }
    @Override
    protected void printDetails() {
        System.out.println("Organizer Name: " + guest.getName());
        System.out.println("Phone Number: " + guest.getPhoneNumber());
        System.out.println("Hall Type: " + bookable.getType());
        System.out.println("Event Type: " + eventType);
        System.out.println("Number of Attendees: " + attendees);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Duration: " + getDuration() + " hours");
        System.out.println("Price: $" + getTotalPrice());
    }

    @Override
    public int compareTo(HallBooking other) {
        return this.startTime.compareTo(other.startTime);
    }

    public static List<HallBooking> getHallBookings() {
        return hallBookings;
    }
}