import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Medicine Inventory - Welcome");
        setSize(460, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(0xC8F7DC);
                g2.setPaint(c1);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        add(panel, BorderLayout.CENTER);

        JLabel title = new JLabel("Medicine Inventory & Reminder System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(new Color(0x143D2A));

        JButton openLogin = styledButton("Open Login / Sign Up");

        openLogin.addActionListener(e -> {
            LoginGUI login = new LoginGUI(this);
            login.setVisible(true);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(8,8,12,8);
        panel.add(title, gbc);
        gbc.gridy = 1;
        panel.add(openLogin, gbc);

        setVisible(true);
    }

    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(0x3FD573));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new RoundedBorder(20));
        b.setPreferredSize(new Dimension(220, 44));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0x01C383)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(new Color(0xFF258E62, true)); }
        });
        return b;
    }

    static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;
        RoundedBorder(int r) { radius = r; }
        public Insets getBorderInsets(Component c) { return new Insets(radius, radius, radius, radius); }
        public boolean isBorderOpaque() { return false; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0x06BD80));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
