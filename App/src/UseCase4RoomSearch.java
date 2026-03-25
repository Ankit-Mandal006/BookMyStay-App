import java.util.ArrayList;
import java.util.List;

class SearchService {
    public void displayAvailableRooms(List<Room> roomDefinitions, RoomInventory inventory) {
        System.out.println("--- Available Rooms Search Results ---");
        for (Room room : roomDefinitions) {
            int availableCount = inventory.getAvailability(room.getRoomType());
            if (availableCount > 0) {
                System.out.println("Room: " + room.getRoomType() + " | Available: " + availableCount);
                System.out.println("Details: " + room.getDescription());
                System.out.println("-----------------------------------");
            }
        }
    }
}

public class UseCase4RoomSearch {
    public static void main(String[] args) {
        RoomInventory hotelInventory = new RoomInventory();
        hotelInventory.initializeRoom("Single Room", 8);
        hotelInventory.initializeRoom("Double Room", 0);
        hotelInventory.initializeRoom("Suite Room", 3);

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        SearchService searchService = new SearchService();
        searchService.displayAvailableRooms(rooms, hotelInventory);
    }
}