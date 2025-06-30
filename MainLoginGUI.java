import javax.swing.*;
import java.awt.*;

public class MainLoginGUI extends JFrame {
    public MainLoginGUI() {
        setTitle("Library System - Select Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton userButton = new JButton("User Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userButton, gbc);

        userButton.addActionListener(e -> {
            dispose();
            new UserLoginGUI().setVisible(true);
        });

        // Add Back button (for consistency, but here it just exits)
        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(backButton, gbc);
        backButton.addActionListener(e -> {
            dispose(); // or System.exit(0);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainLoginGUI().setVisible(true));
    }
} 