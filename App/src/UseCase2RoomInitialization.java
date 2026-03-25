/**
 * @author Ankit Mandal
 * @version 2.0
 */
abstract class Room {
    private String roomType;
    private double price;
    private int capacity;

    public Room(String roomType, double price, int capacity) {
        this.roomType = roomType;
        this.price = price;
        this.capacity = capacity;
    }


    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }


    public abstract void displayFeatures();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1000.0, 1); }
    @Override
    public void displayFeatures() {
        System.out.println("Feature: Compact space, perfect for solo travelers.");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 1800.0, 2); }
    @Override
    public void displayFeatures() {
        System.out.println("Feature: Extra space with a King-sized bed.");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3500.0, 4); }
    @Override
    public void displayFeatures() {
        System.out.println("Feature: Luxury living with a private lounge area.");
    }
}


public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay: Room Initialization (v2.0) ---");


        Room single = new SingleRoom();
        Room b_double = new DoubleRoom();
        Room suite = new SuiteRoom();


        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;


        displayRoomInfo(single, singleAvailability);
        displayRoomInfo(b_double, doubleAvailability);
        displayRoomInfo(suite, suiteAvailability);
    }

    public static void displayRoomInfo(Room room, int availability) {
        System.out.println("\nType: " + room.getRoomType());
        System.out.println("Price: ₹" + room.getPrice());
        System.out.println("Capacity: " + room.getCapacity() + " Person(s)");
        System.out.println("Current Availability: " + availability);
        room.displayFeatures();
    }
}