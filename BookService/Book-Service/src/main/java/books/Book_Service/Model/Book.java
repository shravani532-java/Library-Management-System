package books.Book_Service.Model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int book_id;
    @Column(name="book_name",nullable = false)
    String bookName;
    @Column(nullable = false)
    String author;
    @Column(nullable = false)
    String publisher;
    @Column(nullable = false)
    double price;
    @Lob
    @Column(name = "book_pdf",nullable = false,columnDefinition = "LONGBLOB")
    private byte[] data;

    public Book() {
    }

    public Book(int book_id, String bookName, String author, String publisher, double price, byte[] data) {
        this.book_id = book_id;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.data = data;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
