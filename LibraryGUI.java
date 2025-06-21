import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryGUI extends JFrame {
    private Library library;
    private DefaultListModel<String> userListModel;
    private DefaultListModel<String> bookListModel;
    private DefaultListModel<String> borrowedListModel;
    private boolean adminMode;
    private User currentUser;

    public LibraryGUI(Library library, boolean adminMode, User user) {
        this.library = library;
        this.adminMode = adminMode;
        this.currentUser = user;
        setTitle("Library Management System - " + (adminMode ? "Admin" : "User"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        if (adminMode) {
            tabbedPane.addTab("Books", createBookPanel());
            tabbedPane.addTab("Manage Users & Books", createAdminManagePanel());
        } else {
            tabbedPane.addTab("Borrow/Return", createBorrowPanel());
        }
        add(tabbedPane);
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JTextField idField = new JTextField(8);
        JTextField nameField = new JTextField(12);
        JTextField passwordField = new JTextField(12);
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"user", "admin"});
        JButton addButton = new JButton("Add User");
        userListModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userListModel);
        refreshUserList();

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Role:"));
        inputPanel.add(roleBox);
        inputPanel.add(addButton);

        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = (String) roleBox.getSelectedItem();
            if (!id.isEmpty() && !name.isEmpty() && !password.isEmpty()) {
                library.addUser(new User(id, name, password, role));
                refreshUserList();
                idField.setText("");
                nameField.setText("");
                passwordField.setText("");
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        JTextField idField = new JTextField(12);
        inputPanel.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(12);
        inputPanel.add(titleField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        JTextField authorField = new JTextField(12);
        inputPanel.add(authorField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        JTextField stockField = new JTextField(8);
        inputPanel.add(stockField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Book");
        inputPanel.add(addButton, gbc);
        bookListModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(bookListModel);
        refreshBookList();
        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            int stock = 0;
            try { stock = Integer.parseInt(stockField.getText().trim()); } catch (Exception ex) {}
            if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty() && stock > 0) {
                library.addBook(new Book(id, title, author, stock));
                refreshBookList();
                idField.setText("");
                titleField.setText("");
                authorField.setText("");
                stockField.setText("");
            }
        });
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookList), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // Table of books borrowed by the current user
        String[] columns = {"Book ID", "Title", "Author"};
        java.util.List<Book> borrowedBooks = new java.util.ArrayList<>();
        for (var entry : library.getAllBorrowedBooks().entrySet()) {
            String bookId = entry.getKey();
            String userId = entry.getValue();
            if (userId.equals(currentUser.getId())) {
                for (Book b : library.getBooks()) {
                    if (b.getId().equals(bookId)) {
                        borrowedBooks.add(b);
                        break;
                    }
                }
            }
        }
        String[][] data = new String[borrowedBooks.size()][3];
        for (int i = 0; i < borrowedBooks.size(); i++) {
            Book b = borrowedBooks.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getTitle();
            data[i][2] = b.getAuthor();
        }
        JTable borrowedTable = new JTable(data, columns);
        panel.add(new JLabel("Your Borrowed Books:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(borrowedTable), BorderLayout.CENTER);
        // Borrow/Return controls
        JPanel inputPanel = new JPanel();
        JTextField bookIdField = new JTextField(8);
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        inputPanel.add(new JLabel("Book ID:"));
        inputPanel.add(bookIdField);
        inputPanel.add(borrowButton);
        inputPanel.add(returnButton);
        borrowButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            if (!bookId.isEmpty()) {
                library.borrowBook(bookId, currentUser.getId());
                // Refresh the panel to update the table
                SwingUtilities.getWindowAncestor(panel).dispose();
                new LibraryGUI(library, false, currentUser).setVisible(true);
            }
        });
        returnButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            if (!bookId.isEmpty()) {
                library.returnBook(bookId);
                // Refresh the panel to update the table
                SwingUtilities.getWindowAncestor(panel).dispose();
                new LibraryGUI(library, false, currentUser).setVisible(true);
            }
        });
        panel.add(inputPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshUserList() {
        userListModel.clear();
        for (User user : library.getUsers()) {
            userListModel.addElement(user.toString());
        }
    }

    private void refreshBookList() {
        bookListModel.clear();
        for (Book book : library.getBooks()) {
            bookListModel.addElement(book.toString());
        }
    }

    private void refreshBorrowedList() {
        borrowedListModel.clear();
        for (var entry : library.getAllBorrowedBooks().entrySet()) {
            String bookId = entry.getKey();
            String userId = entry.getValue();
            borrowedListModel.addElement("Book ID: " + bookId + " -> User ID: " + userId);
        }
    }

    private JPanel createAdminManagePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        // Users table
        String[] userColumns = {"User ID", "Name", "Role"};
        java.util.List<User> users = library.getUsers();
        String[][] userData = new String[users.size()][3];
        for (int i = 0; i < users.size(); i++) {
            userData[i][0] = users.get(i).getId();
            userData[i][1] = users.get(i).getName();
            userData[i][2] = users.get(i).getRole();
        }
        JTable usersTable = new JTable(userData, userColumns);
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.add(new JLabel("All Users:"), BorderLayout.NORTH);
        usersPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        // Books table
        String[] bookColumns = {"Book ID", "Title", "Author", "Stock"};
        java.util.List<Book> books = library.getBooks();
        String[][] bookData = new String[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            bookData[i][0] = books.get(i).getId();
            bookData[i][1] = books.get(i).getTitle();
            bookData[i][2] = books.get(i).getAuthor();
            bookData[i][3] = String.valueOf(books.get(i).getStock());
        }
        JTable booksTable = new JTable(bookData, bookColumns);
        JPanel booksPanel = new JPanel(new BorderLayout());
        booksPanel.add(new JLabel("All Books:"), BorderLayout.NORTH);
        booksPanel.add(new JScrollPane(booksTable), BorderLayout.CENTER);
        // Borrowings table
        String[] columns = {"Book ID", "Book Title", "User ID", "User Name"};
        java.util.List<String[]> borrowRows = new java.util.ArrayList<>();
        for (var entry : library.getAllBorrowedBooks().entrySet()) {
            String bookId = entry.getKey();
            String userId = entry.getValue();
            Book book = null;
            User user = null;
            for (Book b : library.getBooks()) if (b.getId().equals(bookId)) book = b;
            for (User u : library.getUsers()) if (u.getId().equals(userId)) user = u;
            borrowRows.add(new String[]{
                bookId,
                book != null ? book.getTitle() : "",
                userId,
                user != null ? user.getName() : ""
            });
        }
        String[][] borrowData = borrowRows.toArray(new String[0][]);
        JTable borrowTable = new JTable(borrowData, columns);
        JPanel borrowPanel = new JPanel(new BorderLayout());
        borrowPanel.add(new JLabel("Current Borrowings:"), BorderLayout.NORTH);
        borrowPanel.add(new JScrollPane(borrowTable), BorderLayout.CENTER);
        panel.add(usersPanel);
        panel.add(booksPanel);
        panel.add(borrowPanel);
        return panel;
    }
} 