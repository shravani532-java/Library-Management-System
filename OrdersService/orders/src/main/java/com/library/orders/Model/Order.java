package com.library.orders.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="ordering")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int orderId;
    int bookId;
    int userId;
    @Enumerated(EnumType.STRING)
    //@Column(columnDefinition = "VARCHAR(255)")
    OrderType orderType;
    LocalDate orderDate;
    @Future(message = "due date must be greater than current date")
    LocalDate dueDate;
    double amount;

    String status;

    public Order() {
    }
    public Order(int orderId, int bookId, int userId, OrderType orderType, LocalDate orderDate, LocalDate dueDate, double amount, String status) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.userId = userId;
        this.orderType = orderType;
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

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", orderType=" + orderType +
                ", orderDate=" + orderDate +
                ", dueDate=" + dueDate +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
