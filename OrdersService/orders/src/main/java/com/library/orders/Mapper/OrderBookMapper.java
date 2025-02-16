package com.library.orders.Mapper;

import com.library.orders.DTO.BookDTO;
import com.library.orders.DTO.OrderDetailsDTO;
import com.library.orders.Model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderBookMapper {
    public OrderDetailsDTO convertToOrderDetailsDTO(Order order, BookDTO book){
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setOrderId(order.getOrderId());
        orderDetailsDTO.setBookId(order.getBookId());
        orderDetailsDTO.setUserId(order.getUserId());
        orderDetailsDTO.setBookName(book.getBookName());
        orderDetailsDTO.setAuthor(book.getAuthor());
        orderDetailsDTO.setPublisher(book.getPublisher());
        orderDetailsDTO.setPrice(book.getPrice());
        orderDetailsDTO.setOredrType(order.getOrderType());
        orderDetailsDTO.setOrderDate(order.getOrderDate());
        orderDetailsDTO.setDueDate(order.getDueDate());
        orderDetailsDTO.setAmount(order.getAmount());
        orderDetailsDTO.setStatus(order.getStatus());

        return orderDetailsDTO;
    }
}
