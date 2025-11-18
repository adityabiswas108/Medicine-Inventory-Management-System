import javax.swing.*;
import java.awt.*;

public class SignUpGUI extends JDialog {

    private final JTextField emailField;
    private final JPasswordField passField;
    private final JPasswordField confirmField;

    public SignUpGUI(Frame parent) {
        super(parent, "Sign Up", true);
        setSize(520, 360);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel top = new JPanel();
        top.setBackground(new Color(0xC8F7DC));
        JLabel title = new JLabel("Create A New Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(new Color(0x143D2A));
        top.add(title);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xC8F7DC));
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(8,8,8,8); gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; center.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; emailField = new JTextField(22); center.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; center.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; passField = new JPasswordField(22); center.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; center.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1; confirmField = new JPasswordField(22); center.add(confirmField, gbc);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(0xC8F7DC));
        JButton create = styledButton("Create Account");
        JButton cancel = styledButton("Cancel");
        bottom.add(create); bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        create.addActionListener(e -> createAccount());
        cancel.addActionListener(e -> dispose());
    }

    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(0x2E8B75));
        b.setBorder(new MainGUI.RoundedBorder(20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 40));
        b.setFocusPainted(false);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x24705D)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x2E8B75)); }
        });
        return b;
    }

    private void createAccount() {
        String email = emailField.getText().trim();
        String p = new String(passField.getPassword()).trim();
        String conf = new String(confirmField.getPassword()).trim();
        if (email.isEmpty() || p.isEmpty() || conf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill All Fields.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Invalid Email.");
            return;
        }
        if (!p.equals(conf)) {
            JOptionPane.showMessageDialog(this, "Passwords Do Not Match.");
            return;
        }
        if (UserHandler.checkUserExists(email)) {
            JOptionPane.showMessageDialog(this, "User Already Exists. Try Login.");
            return;
        }
        UserHandler.addUser(email, p);
        JOptionPane.showMessageDialog(this, "Account Created. You Can Now Login.");
        dispose();
    }
}


