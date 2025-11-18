import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();
        ReminderSystem reminderSystem = new ReminderSystem();

        MedicineDataHandler.loadFromFile(inventory);

        while (true) {
            System.out.println("=== Medicine Inventory & Reminder System ===");
            System.out.println("1. Add Medicine");
            System.out.println("2. Display All Medicines");
            System.out.println("3. Remove Medicine");
            System.out.println("4. Search Medicine By Name");
            System.out.println("5. Check Low Stock");
            System.out.println("6. Check Expired Medicines");
            System.out.println("7. Check Reminders");
            System.out.println("8. Exit");
            System.out.print("Choose An Option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Medicine Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Expiry Date (dd-MM-yyyy): ");
                    String expiry = scanner.nextLine();
                    System.out.print("Enter Dosage: ");
                    String dosage = scanner.nextLine();
                    System.out.print("Enter Dosage Time (HH:mm) Or Leave Empty: ");
                    String dosageTime = scanner.nextLine();

                    Tablet tablet = new Tablet(name, quantity, expiry, dosage);
                    tablet.setDosageTime(dosageTime);
                    inventory.addMedicine(tablet);
                    MedicineDataHandler.saveToFile(inventory);
                    System.out.println("Medicine Added Successfully!");
                    break;

                case "2":
                    if (inventory.getMedicineList().isEmpty()) {
                        System.out.println("No Medicines In Inventory.");
                    } else {
                        for (Medicine m : inventory.getMedicineList()) {
                            System.out.println("--------------------");
                            System.out.println(m.getDisplayInfo());
                        }
                    }
                    break;

                case "3":
                    System.out.print("Enter Name To Remove: ");
                    String removeName = scanner.nextLine();
                    Medicine toRemove = inventory.searchByName(removeName);
                    if (toRemove != null) {
                        inventory.removeMedicine(toRemove);
                        MedicineDataHandler.saveToFile(inventory);
                        System.out.println("Medicine Removed Successfully!");
                    } else {
                        System.out.println("Medicine Not Found In Inventory!");
                    }
                    break;

                case "4":
                    System.out.print("Enter Name To Search: ");
                    String searchName = scanner.nextLine();
                    Medicine found = inventory.searchByName(searchName);
                    if (found != null) {
                        System.out.println(found.getDisplayInfo());
                    } else {
                        System.out.println("Medicine Not Found In Inventory!");
                    }
                    break;

                case "5":
                    System.out.println("=== Low Stock Medicines ===");
                    if (inventory.getLowStockMedicines().isEmpty()) {
                        System.out.println("No Low Stock Medicines.");
                    } else {
                        for (Medicine m : inventory.getLowStockMedicines()) {
                            System.out.println(m.getDisplayInfo());
                        }
                    }
                    break;

                case "6":
                    System.out.println("=== Expired Medicines ===");
                    if (inventory.getExpiredMedicines().isEmpty()) {
                        System.out.println("No Expired Medicines.");
                    } else {
                        for (Medicine m : inventory.getExpiredMedicines()) {
                            System.out.println(m.getDisplayInfo());
                        }
                    }
                    break;

                case "7":
                    String reminders = ReminderSystem.getReminders(inventory);
                    if (reminders.isEmpty()) {
                        System.out.println("No Reminders Now.");
                    } else {
                        System.out.println(reminders);
                    }
                    break;

                case "8":
                    System.out.println("Exiting Program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice! Please Try Again.");
                    break;
            }

            System.out.println();
        }
    }
}

