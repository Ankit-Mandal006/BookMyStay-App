import java.util.ArrayList;
import java.util.List;

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    public void recordBooking(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getHistory() {
        return new ArrayList<>(history);
    }
}

class BookingReportService {
    public void generateSummaryReport(BookingHistory bookingHistory) {
        List<Reservation> records = bookingHistory.getHistory();

        System.out.println("\n--- Final Booking Summary Report ---");
        if (records.isEmpty()) {
            System.out.println("No confirmed bookings found in history.");
            return;
        }

        int totalBookings = records.size();
        System.out.println("Total Confirmed Bookings: " + totalBookings);
        System.out.println("Detailed Audit Trail:");

        for (int i = 0; i < records.size(); i++) {
            System.out.println((i + 1) + ". " + records.get(i));
        }
        System.out.println("------------------------------------");
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation res1 = new Reservation("Ankit", "Suite Room");
        Reservation res2 = new Reservation("Harpreet", "Single Room");
        Reservation res3 = new Reservation("Rahul", "Double Room");

        history.recordBooking(res1);
        history.recordBooking(res2);
        history.recordBooking(res3);

        System.out.println("[System] Recording confirmed bookings to history...");

        reportService.generateSummaryReport(history);
    }
}