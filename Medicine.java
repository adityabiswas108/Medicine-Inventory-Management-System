public class Medicine {
    protected String name;
    protected int quantity;
    protected String expiryDate;
    protected String dosage;
    protected String dosageTime;

    public Medicine(String name, int quantity, String expiryDate, String dosage) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.dosage = dosage;
        this.dosageTime = "";
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getExpiryDate() { return expiryDate; }
    public String getDosage() { return dosage; }
    public String getDosageTime() { return dosageTime; }
    public void setDosageTime(String dosageTime) { this.dosageTime = dosageTime; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getDisplayInfo() {
        return "Name: " + name +
                "\nQuantity: " + quantity +
                "\nExpiry Date: " + expiryDate +
                "\nDosage: " + dosage;
    }
}


