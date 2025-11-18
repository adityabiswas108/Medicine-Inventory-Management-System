import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JDialog {

    private final JTextField emailField;
    private final JPasswordField passwordField;

    public LoginGUI(Frame parent) {
        super(parent, "Login", true);
        setSize(520, 340);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0xC8F7DC));
        top.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        JLabel heading = new JLabel("Welcome — Please Sign In");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(new Color(0x143D2A));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(heading, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xC8F7DC));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(24);
        emailField.setBorder(BorderFactory.createCompoundBorder(new RoundedEmptyBorder(12), emailField.getBorder()));
        center.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        center.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(24);
        passwordField.setBorder(BorderFactory.createCompoundBorder(new RoundedEmptyBorder(12), passwordField.getBorder()));
        center.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel signupHint = new JLabel("<html>Don't Have An Account? <a href=''>Create one</a></html>");
        signupHint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupHint.setForeground(new Color(0x114B33));
        signupHint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new SignUpGUI((Frame) getParent()).setVisible(true);
            }
        });
        center.add(signupHint, gbc);

        gbc.gridy = 3;
        JLabel forgot = new JLabel("<html><a href=''>Forgot Password?</a></html>");
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgot.setForeground(new Color(0x114B33));
        forgot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String email = JOptionPane.showInputDialog(LoginGUI.this, "Enter Your Registered Email:");
                if (email == null || email.isEmpty()) return;
                if (!UserHandler.checkUserExists(email)) {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Email Not Found.");
                    return;
                }
                String np = JOptionPane.showInputDialog(LoginGUI.this, "Enter New Password:");
                if (np == null || np.isEmpty()) return;
                UserHandler.updatePassword(email, np);
                JOptionPane.showMessageDialog(LoginGUI.this, "Password Updated.");
            }
        });
        center.add(forgot, gbc);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(0xC8F7DC));
        JButton loginBtn = styledButton("Login");
        JButton cancelBtn = styledButton("Cancel");
        bottom.add(loginBtn);
        bottom.add(cancelBtn);
        add(bottom, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> attemptLogin());
        cancelBtn.addActionListener(e -> dispose());

        if (!UserHandler.checkUserExists("joy.test@example.com")) {
            UserHandler.addUser("joy.test@example.com", "mypassword123");
        }
    }

    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(0x2E8B75));
        b.setBorder(new MainGUI.RoundedBorder(20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(120, 40));
        b.setFocusPainted(false);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x24705D)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x2E8B75)); }
        });
        return b;
    }

    private void attemptLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill All Fields.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Invalid Email Format.");
            return;
        }
        if (UserHandler.validateUser(email, pass)) {
            Inventory inv = new Inventory();
            MedicineDataHandler.loadFromFile(inv);
            SwingUtilities.invokeLater(() -> new MainMenuGUI(inv, email));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials.");
        }
    }

    static class RoundedEmptyBorder implements javax.swing.border.Border {
        private final int radius;
        RoundedEmptyBorder(int r) { radius = r; }
        public Insets getBorderInsets(Component c) { return new Insets(radius, radius, radius, radius); }
        public boolean isBorderOpaque() { return false; }
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {}
    }
}






