import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inventory {
    private List<Medicine> medicines;

    public Inventory() {
        medicines = new ArrayList<>();
    }

    public void addMedicine(Medicine m) { medicines.add(m); }
    public void removeMedicine(Medicine m) { medicines.remove(m); }
    public List<Medicine> getMedicineList() { return medicines; }

    public Medicine searchByName(String name) {
        for (Medicine m : medicines) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public List<Medicine> getLowStockMedicines() {
        List<Medicine> lowStock = new ArrayList<>();
        for (Medicine m : medicines) {
            if (m.getQuantity() <= 5) lowStock.add(m); // threshold 5
        }
        return lowStock;
    }

    public List<Medicine> getExpiredMedicines() {
        List<Medicine> expired = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Medicine m : medicines) {
            LocalDate expiry = LocalDate.parse(m.getExpiryDate(), formatter);
            if (!expiry.isAfter(today)) expired.add(m);
        }
        return expired;
    }
}


