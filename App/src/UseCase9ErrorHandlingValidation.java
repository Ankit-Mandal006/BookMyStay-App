import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {
    public static void validateRequest(String roomType, RoomInventory inventory) throws InvalidBookingException {
        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Error: Room type '" + roomType + "' does not exist in our records.");
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Error: No availability left for '" + roomType + "'.");
        }
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.initializeRoom("Single Room", 1);
        inventory.initializeRoom("Suite Room", 1);

        String[] testRequests = {"Single Room", "single room", "Penthouse", "Single Room"};

        System.out.println("--- System Validation Log ---");

        for (String requestType : testRequests) {
            try {
                System.out.println("\n[Processing] Request for: " + requestType);

                BookingValidator.validateRequest(requestType, inventory);

                int current = inventory.getAvailability(requestType);
                inventory.updateAvailability(requestType, current - 1);
                System.out.println("[SUCCESS] Booking confirmed for " + requestType);

            } catch (InvalidBookingException e) {
                System.err.println("[VALIDATION FAILED] " + e.getMessage());
            } catch (Exception e) {
                System.err.println("[SYSTEM ERROR] An unexpected error occurred.");
            }
        }

        System.out.println("\n--- Final Inventory State ---");
        inventory.displayInventory();
    }
}