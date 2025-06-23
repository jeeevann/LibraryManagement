import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();
        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Register User");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> {
                    System.out.print("Book ID: ");
                    String id = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Stock: ");
                    int stock = sc.nextInt();
                    sc.nextLine(); // consume newline
                    lib.addBook(new Book(id, title, author, stock));
                }
                case 2 -> {
                    System.out.print("User ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    lib.registerUser(new User(id, name, password, "user"));
                }
                case 3 -> {
                    System.out.print("Enter Book ID to issue: ");
                    String bookId = sc.nextLine();
                    System.out.print("Enter User ID: ");
                    String userId = sc.nextLine();
                    lib.issueBook(bookId, userId);
                }
                case 4 -> {
                    System.out.print("Enter Book ID to return: ");
                    String bookId = sc.nextLine();
                    lib.returnBook(bookId);
                }
                case 5 -> lib.showBooks();
                case 6 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
} 