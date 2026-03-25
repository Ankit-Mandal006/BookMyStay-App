import java.util.*;

class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return serviceName + " (₹" + price + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationAddOns;

    public AddOnServiceManager() {
        this.reservationAddOns = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, Service service) {
        reservationAddOns.putIfAbsent(reservationId, new ArrayList<>());
        reservationAddOns.get(reservationId).add(service);
        System.out.println("[Add-On] " + service.getServiceName() + " added to Reservation: " + reservationId);
    }

    public void displayReservationDetails(String reservationId) {
        System.out.println("\n--- Add-On Details for " + reservationId + " ---");
        List<Service> services = reservationAddOns.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        double totalExtra = 0;
        for (Service s : services) {
            System.out.println("- " + s);
            totalExtra += s.getPrice();
        }
        System.out.println("Total Add-On Cost: ₹" + totalExtra);
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        Service breakfast = new Service("Buffet Breakfast", 500.0);
        Service spa = new Service("Spa Treatment", 1500.0);
        Service pickup = new Service("Airport Pickup", 800.0);

        String resId1 = "SUI-101";
        String resId2 = "SIN-102";

        manager.addServiceToReservation(resId1, breakfast);
        manager.addServiceToReservation(resId1, spa);
        manager.addServiceToReservation(resId2, pickup);

        manager.displayReservationDetails(resId1);
        manager.displayReservationDetails(resId2);
    }
}