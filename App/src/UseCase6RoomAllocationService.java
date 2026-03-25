import java.util.*;

class AllocationService {
    private RoomInventory inventory;
    private BookingRequestQueue requestQueue;
    private Map<String, Set<String>> allocatedRooms;

    public AllocationService(RoomInventory inventory, BookingRequestQueue requestQueue) {
        this.inventory = inventory;
        this.requestQueue = requestQueue;
        this.allocatedRooms = new HashMap<>();

        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    public void processAllocations() {
        System.out.println("\n--- Processing Room Allocations ---");
        Queue<Reservation> queue = requestQueue.getQueue();

        while (!queue.isEmpty()) {
            Reservation request = queue.poll();
            String type = request.getRoomType();
            int available = inventory.getAvailability(type);

            if (available > 0) {
                String roomId = type.substring(0, 3).toUpperCase() + "-" + (100 + allocatedRooms.get(type).size() + 1);

                if (!allocatedRooms.get(type).contains(roomId)) {
                    allocatedRooms.get(type).add(roomId);
                    inventory.updateAvailability(type, available - 1);

                    System.out.println("[CONFIRMED] Guest: " + request.getGuestName() +
                            " | Assigned: " + roomId +
                            " | Remaining " + type + "s: " + (available - 1));
                }
            } else {
                System.out.println("[FAILED] Guest: " + request.getGuestName() +
                        " | Reason: No " + type + " available.");
            }
        }
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.initializeRoom("Single Room", 2);
        inventory.initializeRoom("Suite Room", 1);

        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Ankit", "Suite Room"));
        queue.addRequest(new Reservation("Harpreet", "Suite Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));

        AllocationService service = new AllocationService(inventory, queue);
        service.processAllocations();
    }
}