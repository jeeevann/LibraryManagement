import java.util.*;
import java.io.*;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private final String BOOK_FILE = "data/books.txt";
    private final String USER_FILE = "data/users.txt";
    private final String BORROWED_FILE = "data/borrowed.txt";
    private Map<String, String> borrowedBooks = new HashMap<>(); // bookId -> userId

    public Library() {
        loadData();
    }

    public void addBook(Book book) {
        books.add(book);
        saveData();
    }

    public void registerUser(User user) {
        users.add(user);
        saveData();
    }

    public void issueBook(String bookId, String userId) {
        if (!borrowedBooks.containsKey(bookId)) {
            borrowedBooks.put(bookId, userId);
            System.out.println("Book issued.");
            saveData();
        } else {
            System.out.println("Book not available.");
        }
    }

    public void showBooks() {
        books.forEach(System.out::println);
    }

    public void addUser(User user) {
        users.add(user);
        saveData();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean borrowBook(String bookId, String userId) {
        for (Book book : books) {
            if (book.getId().equals(bookId) && book.getStock() > 0) {
                if (!borrowedBooks.containsKey(bookId)) {
                    borrowedBooks.put(bookId, userId);
                    book.setStock(book.getStock() - 1);
                    saveData();
                    return true;
                }
            }
        }
        return false;
    }

    public String getBorrower(String bookId) {
        return borrowedBooks.get(bookId);
    }

    public Map<String, String> getAllBorrowedBooks() {
        return borrowedBooks;
    }

    public User authenticate(String id, String password) {
        for (User user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean returnBook(String bookId) {
        if (borrowedBooks.containsKey(bookId)) {
            for (Book book : books) {
                if (book.getId().equals(bookId)) {
                    book.setStock(book.getStock() + 1);
                    break;
                }
            }
            borrowedBooks.remove(bookId);
            saveData();
            return true;
        }
        return false;
    }

    private void saveData() {
        try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(BOOK_FILE));
             ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(USER_FILE));
             ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream(BORROWED_FILE))) {
            out1.writeObject(books);
            out2.writeObject(users);
            out3.writeObject(borrowedBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            File bookFile = new File(BOOK_FILE);
            File userFile = new File(USER_FILE);
            File borrowedFile = new File(BORROWED_FILE);
            if (bookFile.exists()) {
                ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(BOOK_FILE));
                books = (List<Book>) in1.readObject();
                in1.close();
            }
            if (userFile.exists()) {
                ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(USER_FILE));
                users = (List<User>) in2.readObject();
                in2.close();
            }
            if (borrowedFile.exists()) {
                ObjectInputStream in3 = new ObjectInputStream(new FileInputStream(BORROWED_FILE));
                borrowedBooks = (Map<String, String>) in3.readObject();
                in3.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
} 