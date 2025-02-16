package com.library.orders.Service;

import com.library.orders.Client.BookClient;
import com.library.orders.DTO.BookDTO;
import com.library.orders.DTO.OrderDTO;
import com.library.orders.DTO.OrderDetailsDTO;
import com.library.orders.Exception.OrderNotFoundException;
import com.library.orders.Exception.WrongOrderType;
import com.library.orders.Mapper.OrderBookMapper;
import com.library.orders.Mapper.OrderMapper;
import com.library.orders.Model.Order;
import com.library.orders.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderBookMapper orderBookMapper;

    @Autowired
    BookClient bookClient;

   // Order order;
    public Order orderBook(OrderDTO orderDTO) throws WrongOrderType, OrderNotFoundException {
        System.out.println("in service order");
        Order order = orderMapper.orderDtoToModel(orderDTO);
        order.setOrderDate(LocalDate.now());
        //orderRepository.save(order);
        System.out.println("ordertype "+order.getOrderType());
        switch(order.getOrderType()) {
            case BUY:
                System.out.println("buying case");
                order.setAmount(bookClient.bookPrice(order.getBookId()));
                break;
            case RENT:
                System.out.println("rent case");
                int days = Period.between(order.getOrderDate(), order.getDueDate()).getDays();
                order.setAmount(days * 5);
                break;
            default:
                System.out.println("default case");
                throw new WrongOrderType("please provide buy or rent");
        }
        /*switch (order.getOrderType()) {
            case BUY -> order.setAmount(bookClient.bookPrice(order.getBookId()));
            case RENT -> {
                int days = Period.between(order.getOrderDate(), order.getDueDate()).getDays();
                order.setAmount(days * 5);
            }
            default -> throw new WrongOrderType("please provide buy or rent");
        }*/
        System.out.println("please pay the amount");
        order.setStatus("order completed");
        orderRepository.save(order);
        System.out.println("in service order completed");
        return order;
    }

    public OrderDetailsDTO getOrderById(int oid) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(oid);
        System.out.println("order details "+optionalOrder);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + oid));

        // Fetch book details using BookService
        ResponseEntity<BookDTO> response = bookClient.getBookDetails(order.getBookId());
        if (response.getStatusCode().is2xxSuccessful()) {
            BookDTO book = response.getBody();

            // Create OrderDetailsDTO combining order and book details
             return orderBookMapper.convertToOrderDetailsDTO(order, book);

        } else {
            throw new RuntimeException("Failed to fetch book details. Status code: " + response.getStatusCode());
        }
    }

    public void cancelOrder(int oid) throws OrderNotFoundException {
        Order order = orderRepository.findById(oid).orElseThrow(()->new OrderNotFoundException("Order not found with id: " + oid));
        //implement like order type is rent and calculate the amount refund remaining
        order.setStatus("cancelled");
        orderRepository.save(order);
    }

    public List<OrderDetailsDTO> getUserOrders(int uID) throws OrderNotFoundException {
        List<Order> orders = orderRepository.findAllByUserId(uID);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for user ID: " + uID);
        }
       System.out.println("order recieved "+orders);
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        for (Order order : orders) {
            System.out.println("in loop");
            ResponseEntity<BookDTO> response = bookClient.getBookDetails(order.getBookId());
            System.out.println("book "+order.getBookId());
            if (response.getStatusCode().is2xxSuccessful()) {
                BookDTO book = response.getBody();
                System.out.println(book);
                // Create OrderDetailsDTO combining order and book details
                OrderDetailsDTO orderDetailsDTO = orderBookMapper.convertToOrderDetailsDTO(order, book);
                orderDetailsList.add(orderDetailsDTO);
            } else {
                throw new RuntimeException("Failed to fetch book details for book ID: " + order.getBookId() + ". Status code: " + response.getStatusCode());
            }
        }
        return orderDetailsList;
    }
}
