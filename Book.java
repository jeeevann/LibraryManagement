import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private int stock;

    public Book(String id, String title, String author, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author + " (Stock: " + stock + ")";
    }
} 