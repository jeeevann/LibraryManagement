import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private Library library;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginGUI() {
        library = new Library();
        setTitle("Library Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("User ID:");
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
        User user = library.authenticate(id, password);
        if (user != null) {
            dispose();
            if (user.getRole().equalsIgnoreCase("admin")) {
                new LibraryGUI(library, true, user).setVisible(true);
            } else {
                new LibraryGUI(library, false, user).setVisible(true);
            }
        } else {
            messageLabel.setText("Invalid ID or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
} 