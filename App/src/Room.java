public abstract class Room {
    private String roomType;
    private double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public abstract String getDescription();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1000.0); }
    public String getDescription() { return "Perfect for solo travelers."; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 1800.0); }
    public String getDescription() { return "Features a King-sized bed."; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3500.0); }
    public String getDescription() { return "Luxury living with a private lounge."; }
}