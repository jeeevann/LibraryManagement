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
                    lib.addBook(new Book(id, title, author));
                }
                case 2 -> {
                    System.out.print("User ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    lib.registerUser(new User(id, name));
                }
                case 3 -> {
                    System.out.print("Enter Book ID to issue: ");
                    String bookId = sc.nextLine();
                    lib.issueBook(bookId);
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