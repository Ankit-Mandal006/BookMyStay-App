import java.util.*;

class ConcurrentBookingProcessor implements Runnable {
    private RoomInventory inventory;
    private BookingRequestQueue requestQueue;
    private String threadName;

    public ConcurrentBookingProcessor(String name, RoomInventory inventory, BookingRequestQueue queue) {
        this.threadName = name;
        this.inventory = inventory;
        this.requestQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Reservation request;


            synchronized (requestQueue) {
                if (requestQueue.getQueue().isEmpty()) {
                    break;
                }
                request = requestQueue.getQueue().poll();
            }

            if (request != null) {
                processRequest(request);
            }
        }
    }

    private void processRequest(Reservation request) {
        String type = request.getRoomType();


        synchronized (inventory) {
            int available = inventory.getAvailability(type);

            if (available > 0) {

                try { Thread.sleep(100); } catch (InterruptedException e) { }

                inventory.updateAvailability(type, available - 1);
                System.out.println("[" + threadName + "] SUCCESS: " + request.getGuestName() +
                        " booked " + type + ". Remaining: " + (available - 1));
            } else {
                System.out.println("[" + threadName + "] FAILED: No " + type + " for " + request.getGuestName());
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        inventory.initializeRoom("Suite Room", 2);

        BookingRequestQueue sharedQueue = new BookingRequestQueue();
        sharedQueue.addRequest(new Reservation("User_A", "Suite Room"));
        sharedQueue.addRequest(new Reservation("User_B", "Suite Room"));
        sharedQueue.addRequest(new Reservation("User_C", "Suite Room"));
        sharedQueue.addRequest(new Reservation("User_D", "Suite Room"));

        System.out.println("\n--- Starting Concurrent Simulation ---");


        Thread thread1 = new Thread(new ConcurrentBookingProcessor("Thread-1", inventory, sharedQueue));
        Thread thread2 = new Thread(new ConcurrentBookingProcessor("Thread-2", inventory, sharedQueue));

        thread1.start();
        thread2.start();


        thread1.join();
        thread2.join();

        System.out.println("\n--- Final Inventory Check ---");
        inventory.displayInventory();
    }
}