package com.library.orders.DTO;

import com.library.orders.Model.OrderType;
import jakarta.validation.constraints.Future;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderDTO {
    int bookId;
    int userId;
    OrderType oredrType;
    //this is current date
    // LocalDate orderDate;
    LocalDate dueDate;
    //this is calculated based on input
    // double amount;

    public OrderDTO() {
    }

    public OrderDTO(int bookId, int userId, OrderType oredrType, LocalDate dueDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.oredrType = oredrType;
        this.dueDate = dueDate;
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

    public OrderType getOredrType() {
        return oredrType;
    }

    public void setOredrType(OrderType oredrType) {
        this.oredrType = oredrType;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "bookId=" + bookId +
                ", userId=" + userId +
                ", oredrType=" + oredrType +
                ", dueDate=" + dueDate +
                '}';
    }
}
