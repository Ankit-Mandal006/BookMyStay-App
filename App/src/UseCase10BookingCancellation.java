import java.util.*;

class CancellationService {
    private RoomInventory inventory;
    private Stack<String> rollbackStack;
    private Map<String, String> activeBookings;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
        this.activeBookings = new HashMap<>();
    }

    public void confirmBooking(String guestName, String roomType, String roomId) {
        activeBookings.put(guestName, roomType + ":" + roomId);
    }

    public void cancelBooking(String guestName) {
        System.out.println("\n[Processing] Cancellation for Guest: " + guestName);

        if (!activeBookings.containsKey(guestName)) {
            System.err.println("[ERROR] No active reservation found for " + guestName);
            return;
        }

        String bookingData = activeBookings.remove(guestName);
        String[] parts = bookingData.split(":");
        String roomType = parts[0];
        String roomId = parts[1];

        rollbackStack.push(roomId);

        int currentCount = inventory.getAvailability(roomType);
        inventory.updateAvailability(roomType, currentCount + 1);

        System.out.println("[SUCCESS] Cancellation complete.");
        System.out.println("[Rollback] Room " + roomId + " pushed to rollback stack.");
        System.out.println("[Inventory] " + roomType + " count restored to " + (currentCount + 1));
    }

    public void displayRollbackStatus() {
        System.out.println("\n--- Current Rollback Stack (LIFO) ---");
        if (rollbackStack.isEmpty()) {
            System.out.println("No rooms in rollback stack.");
        } else {
            System.out.println("Recently Released Room IDs: " + rollbackStack);
        }
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.initializeRoom("Single Room", 9);

        CancellationService cancellationService = new CancellationService(inventory);

        cancellationService.confirmBooking("Ankit", "Single Room", "SIN-101");
        cancellationService.confirmBooking("Rahul", "Single Room", "SIN-102");

        System.out.println("--- Initial State ---");
        System.out.println("Ankit assigned SIN-101");
        System.out.println("Rahul assigned SIN-102");
        inventory.displayInventory();

        cancellationService.cancelBooking("Rahul");
        cancellationService.cancelBooking("Ankit");

        cancellationService.displayRollbackStatus();

        System.out.println("\n--- Final State ---");
        inventory.displayInventory();

        cancellationService.cancelBooking("Unknown Guest");
    }
}