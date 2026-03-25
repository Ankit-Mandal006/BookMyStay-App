import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public void initializeRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }

    public void displayInventory() {
        System.out.println("--- Current Room Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

public class UseCase3InventorySetup {
    public static void main(String[] args) {
        RoomInventory hotelInventory = new RoomInventory();

        hotelInventory.initializeRoom("Single Room", 10);
        hotelInventory.initializeRoom("Double Room", 5);
        hotelInventory.initializeRoom("Suite Room", 2);

        hotelInventory.displayInventory();

        int currentDoubleRooms = hotelInventory.getAvailability("Double Room");
        hotelInventory.updateAvailability("Double Room", currentDoubleRooms - 1);

        System.out.println("\n[Update] One Double Room booked.");
        hotelInventory.displayInventory();
    }
}