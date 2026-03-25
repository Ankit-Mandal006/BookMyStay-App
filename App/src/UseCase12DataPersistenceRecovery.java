import java.io.*;
import java.util.*;

class PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    public void saveState(RoomInventory inventory, List<Reservation> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("[Persistence] System state saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("[Error] Failed to save state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadState(RoomInventory[] inventory, List<Reservation>[] history) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[Recovery] No persistence file found. Starting with fresh state.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory[0] = (RoomInventory) ois.readObject();
            history[0] = (List<Reservation>) ois.readObject();
            System.out.println("[Recovery] System state restored successfully from " + FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Error] Recovery failed: " + e.getMessage());
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();


        RoomInventory[] inventoryRef = new RoomInventory[1];
        List<Reservation>[] historyRef = new List[1];


        persistence.loadState(inventoryRef, historyRef);

        RoomInventory inventory = (inventoryRef[0] != null) ? inventoryRef[0] : new RoomInventory();
        List<Reservation> history = (historyRef[0] != null) ? historyRef[0] : new ArrayList<>();

        if (inventoryRef[0] == null) {
            System.out.println("[Init] Initializing new inventory...");
            inventory.initializeRoom("Single Room", 10);
            inventory.initializeRoom("Suite Room", 5);
        }


        System.out.println("\n[Activity] Adding a new reservation for 'Zoya'...");
        history.add(new Reservation("Zoya", "Suite Room"));
        inventory.updateAvailability("Suite Room", inventory.getAvailability("Suite Room") - 1);


        inventory.displayInventory();
        System.out.println("History Count: " + history.size());


        persistence.saveState(inventory, history);
        System.out.println("[System] Shutting down. Run the program again to see data recovery!");
    }
}