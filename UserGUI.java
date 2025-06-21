import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserGUI extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField passwordField;
    private JComboBox<String> roleBox;
    private JButton createButton;
    private JLabel resultLabel;

    public UserGUI() {
        setTitle("User Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc);

        idField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(idField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(roleLabel, gbc);

        roleBox = new JComboBox<>(new String[]{"user", "admin"});
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(roleBox, gbc);

        createButton = new JButton("Create User");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(createButton, gbc);

        resultLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(resultLabel, gbc);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String password = passwordField.getText();
                String role = (String) roleBox.getSelectedItem();
                if (id.isEmpty() || name.isEmpty() || password.isEmpty()) {
                    resultLabel.setText("Please enter ID, Name, and Password.");
                } else {
                    User user = new User(id, name, password, role);
                    resultLabel.setText(user.toString());
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserGUI().setVisible(true);
        });
    }
} 