import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainMenuGUI extends JFrame {

    private final Inventory inventory;
    private final String userEmail;
    private final JLabel status;

    public MainMenuGUI(Inventory inventory, String userEmail) {
        this.inventory = inventory;
        this.userEmail = userEmail;

        setTitle("Dashboard - Medicine Inventory");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel main = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(0xC8F7DC);
                Color c2 = new Color(0xECFFF2);
                g2.setPaint(new java.awt.GradientPaint(0,0,c1,0,getHeight(),c2));
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };
        main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        main.setLayout(new BorderLayout(12,12));
        add(main, BorderLayout.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Medicine Inventory & Reminder System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0x143D2A));
        header.add(title, BorderLayout.WEST);
        JLabel u = new JLabel("User: " + userEmail);
        u.setForeground(new Color(0x1F5F3A));
        header.add(u, BorderLayout.EAST);
        main.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2,4,14,14));
        grid.setOpaque(false);

        JButton add = makeBtn("Add Medicine");
        JButton disp = makeBtn("Display All Medicines");
        JButton rem = makeBtn("Remove Medicine");
        JButton search = makeBtn("Search Medicine");
        JButton low = makeBtn("Low Stock");
        JButton exp = makeBtn("Expired Medicines");
        JButton remind = makeBtn("Check Reminders");
        JButton logout = makeBtn("Logout");

        grid.add(add); grid.add(disp); grid.add(rem); grid.add(search);
        grid.add(low); grid.add(exp); grid.add(remind); grid.add(logout);
        main.add(grid, BorderLayout.CENTER);

        status = new JLabel("Welcome! Ready.");
        status.setForeground(new Color(0x1F6B45));
        main.add(status, BorderLayout.SOUTH);

        add.addActionListener(e -> addMedicineDialog());
        disp.addActionListener(e -> displayAll());
        rem.addActionListener(e -> removeMedicine());
        search.addActionListener(e -> searchMedicine());
        low.addActionListener(e -> checkLowStock());
        exp.addActionListener(e -> checkExpired());
        remind.addActionListener(e -> checkReminders());
        logout.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JButton makeBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(new Color(0x2E8B75));
        b.setForeground(Color.WHITE);
        b.setBorder(new MainGUI.RoundedBorder(20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        b.setToolTipText(text);
        b.setPreferredSize(new Dimension(180, 44));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x24705D)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x2E8B75)); }
        });
        return b;
    }

    private void addMedicineDialog() {
        JPanel p = new JPanel(new GridLayout(0,1,6,6));
        JTextField nameF = new JTextField();
        JTextField qtyF = new JTextField();
        JTextField expiryF = new JTextField(); // dd-MM-yyyy
        JTextField dosageF = new JTextField();
        p.add(new JLabel("Name:")); p.add(nameF);
        p.add(new JLabel("Quantity:")); p.add(qtyF);
        p.add(new JLabel("Expiry (dd-MM-yyyy):")); p.add(expiryF);
        p.add(new JLabel("Dosage:")); p.add(dosageF);

        int r = JOptionPane.showConfirmDialog(this, p, "Add Medicine", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            try {
                String name = nameF.getText().trim();
                int qty = Integer.parseInt(qtyF.getText().trim());
                String expiry = expiryF.getText().trim();
                String dosage = dosageF.getText().trim();
                Medicine m = new Tablet(name, qty, expiry, dosage);
                inventory.addMedicine(m);
                MedicineDataHandler.saveToFile(inventory);
                status.setText("Added: " + name);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity Must Be A Number.");
            }
        }
    }

    private void displayAll() {
        List<Medicine> meds = inventory.getMedicineList();
        if (meds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Medicines In Inventory.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Medicine m : meds) sb.append(m.getDisplayInfo()).append("\n\n");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(820, 420));
        JOptionPane.showMessageDialog(this, sp, "All Medicines", JOptionPane.PLAIN_MESSAGE);
    }

    private void removeMedicine() {
        String name = JOptionPane.showInputDialog(this, "Enter The Name To Remove:");
        if (name == null || name.trim().isEmpty()) return;
        Medicine m = inventory.searchByName(name.trim());
        if (m == null) { JOptionPane.showMessageDialog(this, "Medicine Not Found."); return; }
        int c = JOptionPane.showConfirmDialog(this, "Delete " + name + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            inventory.removeMedicine(m);
            MedicineDataHandler.saveToFile(inventory);
            status.setText("Removed: " + name);
        }
    }

    private void searchMedicine() {
        String name = JOptionPane.showInputDialog(this, "Enter Name To Search:");
        if (name == null || name.trim().isEmpty()) return;
        Medicine m = inventory.searchByName(name.trim());
        if (m == null) JOptionPane.showMessageDialog(this, "Not Found.");
        else JOptionPane.showMessageDialog(this, m.getDisplayInfo());
    }

    private void checkLowStock() {
        List<Medicine> low = inventory.getLowStockMedicines();
        if (low.isEmpty()) { JOptionPane.showMessageDialog(this, "No Low Stock Medicines."); return; }
        StringBuilder sb = new StringBuilder();
        for (Medicine m : low) sb.append(m.getDisplayInfo()).append("\n\n");
        JTextArea ta = new JTextArea(sb.toString()); ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta); sp.setPreferredSize(new Dimension(820,420));
        JOptionPane.showMessageDialog(this, sp, "Low Stock", JOptionPane.WARNING_MESSAGE);
    }

    private void checkExpired() {
        List<Medicine> exp = inventory.getExpiredMedicines();
        if (exp.isEmpty()) { JOptionPane.showMessageDialog(this, "No Expired Medicines."); return; }
        StringBuilder sb = new StringBuilder();
        for (Medicine m : exp) sb.append(m.getDisplayInfo()).append("\n\n");
        JTextArea ta = new JTextArea(sb.toString()); ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta); sp.setPreferredSize(new Dimension(820,420));
        JOptionPane.showMessageDialog(this, sp, "Expired Medicines", JOptionPane.ERROR_MESSAGE);
    }

    private void checkReminders() {
        String reminders = ReminderSystem.getReminders(inventory);
        if (reminders == null || reminders.isEmpty()) JOptionPane.showMessageDialog(this, "No Reminders Now.");
        else JOptionPane.showMessageDialog(this, reminders, "Reminders", JOptionPane.INFORMATION_MESSAGE);
    }
}









