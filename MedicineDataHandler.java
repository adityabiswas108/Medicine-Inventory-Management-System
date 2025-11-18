import java.io.*;
import java.util.*;

public class MedicineDataHandler {

    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "medicine.txt";

    public static void saveToFile(Inventory inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Medicine med : inventory.getMedicineList()) {
                writer.write(med.getName() + "," +
                        med.getQuantity() + "," +
                        med.getExpiryDate() + "," +
                        med.getDosage());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error Saving Medicines: " + e.getMessage());
        }
    }

    public static void loadFromFile(Inventory inventory) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error Creating Medicine File: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String name = parts[0];
                    int quantity;
                    try {
                        quantity = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                    String expiry = parts[2];
                    String dosage = parts.length == 4 ? parts[3] : String.join(",", Arrays.copyOfRange(parts, 3, parts.length));

                    Medicine med = new Medicine(name, quantity, expiry, dosage);
                    inventory.addMedicine(med);
                }
            }
        } catch (IOException e) {
            System.out.println("Error Loading Medicines: " + e.getMessage());
        }
    }

    public static String getFilePath() {
        return FILE_PATH;
    }
}




