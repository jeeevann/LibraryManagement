import javax.swing.*;
import java.awt.*;

public class UserLoginGUI extends JFrame {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private Library library;

    // Fields for new user creation
    private JTextField newIdField;
    private JTextField newNameField;
    private JTextField newPasswordField;
    private JButton createUserButton;
    private JLabel createUserLabel;

    public UserLoginGUI() {
        library = new Library();
        setTitle("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Login section
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

        // Separator
        JSeparator sep = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(sep, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // New user section
        JLabel newUserLabel = new JLabel("Create New User");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(newUserLabel, gbc);

        JLabel newIdLabel = new JLabel("User ID:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(newIdLabel, gbc);
        newIdField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(newIdField, gbc);

        JLabel newNameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(newNameLabel, gbc);
        newNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(newNameField, gbc);

        JLabel newPasswordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(newPasswordLabel, gbc);
        newPasswordField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(newPasswordField, gbc);

        createUserButton = new JButton("Create User");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        add(createUserButton, gbc);

        createUserLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        add(createUserLabel, gbc);

        loginButton.addActionListener(e -> handleLogin());
        createUserButton.addActionListener(e -> handleCreateUser());
    }

    private void handleLogin() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (id.equals("admin") && password.equals("admin@123")) {
            dispose();
            User adminUser = new User("admin", "Admin", "admin@123", "admin");
            new LibraryGUI(library, true, adminUser).setVisible(true);
            return;
        }
        User user = library.authenticate(id, password);
        if (user != null && user.getRole().equalsIgnoreCase("user")) {
            dispose();
            new LibraryGUI(library, false, user).setVisible(true);
        } else {
            messageLabel.setText("Invalid user credentials.");
        }
    }

    private void handleCreateUser() {
        String id = newIdField.getText().trim();
        String name = newNameField.getText().trim();
        String password = newPasswordField.getText().trim();
        if (!id.isEmpty() && !name.isEmpty() && !password.isEmpty()) {
            if (id.equalsIgnoreCase("admin")) {
                createUserLabel.setText("Cannot register as admin.");
                return;
            }
            // Check for duplicate user ID
            boolean exists = false;
            for (User user : library.getUsers()) {
                if (user.getId().equals(id)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                createUserLabel.setText("User ID already exists. Choose another.");
            } else {
                library.addUser(new User(id, name, password, "user"));
                createUserLabel.setText("User created! You can now log in.");
                newIdField.setText("");
                newNameField.setText("");
                newPasswordField.setText("");
            }
        } else {
            createUserLabel.setText("Please fill all fields.");
        }
    }
} 