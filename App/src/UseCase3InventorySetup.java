import java.util.HashMap;
import java.util.Map;


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