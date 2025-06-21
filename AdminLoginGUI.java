import javax.swing.*;
import java.awt.*;

public class AdminLoginGUI extends JFrame {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private Library library;

    public AdminLoginGUI() {
        library = new Library();
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Admin ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc);

        idField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(idField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        messageLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(messageLabel, gbc);

        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (id.equals("admin") && password.equals("admin@123")) {
            dispose();
            User adminUser = new User("admin", "Admin", "admin@123", "admin");
            new LibraryGUI(library, true, adminUser).setVisible(true);
        } else {
            messageLabel.setText("Invalid admin credentials.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminLoginGUI().setVisible(true));
    }
} 