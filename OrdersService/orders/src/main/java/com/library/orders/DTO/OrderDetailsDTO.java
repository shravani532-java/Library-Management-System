package com.library.orders.DTO;

import com.library.orders.Model.OrderType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderDetailsDTO {
    int orderId;
    int bookId;
    int userId;
    String bookName;
    String author;
    String publisher;
    double price;
    OrderType oredrType;
    LocalDate orderDate;
    LocalDate dueDate;
    double amount;
    String status;

    public OrderDetailsDTO() {
    }
    public OrderDetailsDTO(int orderId, int bookId, int userId, String bookName, String author, String publisher, double price, OrderType oredrType, LocalDate orderDate, LocalDate dueDate, double amount, String status) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.userId = userId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.oredrType = oredrType;
        this.orderDate = orderDate;
        this.dueDate = dueDate;
        this.amount = amount;
        this.status = status;
    }


    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public OrderType getOredrType() {
        return oredrType;
    }
    public void setOredrType(OrderType oredrType) {
        this.oredrType = oredrType;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getStatus() {return status;}
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDetailsDTO{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", oredrType=" + oredrType +
                ", orderDate=" + orderDate +
                ", dueDate=" + dueDate +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
