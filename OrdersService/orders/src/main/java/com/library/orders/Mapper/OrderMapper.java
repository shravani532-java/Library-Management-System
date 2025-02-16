package com.library.orders.Mapper;

import com.library.orders.DTO.OrderDTO;
import com.library.orders.DTO.OrderDetailsDTO;
import com.library.orders.Model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order orderDtoToModel(OrderDTO orderDTO){
        Order order = new Order();
        order.setBookId(orderDTO.getBookId());
        order.setUserId(orderDTO.getUserId());
        order.setOrderType(orderDTO.getOredrType());
        //order.setOrderDate(orderDTO.getOrderDate());
        order.setDueDate(orderDTO.getDueDate());
        //order.setAmount(orderDTO.getAmount());
        return order;
    }

   /* public OrderDTO orderModelToDto(Order order){
        OrderDTO orderDTO = new OrderDTO(order.getBookId(),order.getUserId(),order.getOrderType(),order.getOrderDate(),order.getDueDate(),order.getAmount());
        return orderDTO;
    }*/
}
