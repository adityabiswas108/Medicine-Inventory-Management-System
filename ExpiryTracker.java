import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpiryTracker {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void checkExpired(Inventory inventory) {
        System.out.println("=== Checking Expired Medicines ===");

        boolean anyExpired = false;

        for (Medicine m : inventory.getMedicineList()) {
            LocalDate expiryDate = LocalDate.parse(m.getExpiryDate(), formatter);
            LocalDate today = LocalDate.now();

            if (expiryDate.isBefore(today)) {
                System.out.println(m.getName() + " Is Expired!");
                anyExpired = true;
            }
        }

        if (!anyExpired) {
            System.out.println("No Expired Medicines Found!");
        }
    }
}
