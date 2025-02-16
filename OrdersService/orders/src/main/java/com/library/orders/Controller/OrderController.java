package com.library.orders.Controller;

import com.library.orders.DTO.OrderDTO;
import com.library.orders.DTO.OrderDetailsDTO;
import com.library.orders.Exception.OrderNotFoundException;
import com.library.orders.Model.Order;
import com.library.orders.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "orderBookFallback")
    @PostMapping("/orderBook")
    public ResponseEntity<Order> orderBook(@RequestBody OrderDTO orderDTO){
        try{
            System.out.println("in controller order");
            Order order = orderService.orderBook(orderDTO);
            System.out.println("order saved "+order);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
        catch (Exception e){
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ORDER NOT SAVED");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //fallback
    public ResponseEntity<Order> orderBookFallback(OrderDTO orderDTO, Throwable t){
        Order fallbackOrder = new Order();
        fallbackOrder.setStatus("Order could not be processed. Please try again later.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackOrder);
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getOrderDetailsFallback")
    @GetMapping("/orderByorderId/{order_id}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable("order_id") Integer orderId) throws OrderNotFoundException {
        try{
            //orderService.getOrderById(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(orderId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //fallback
    public ResponseEntity<OrderDetailsDTO> getOrderDetailsFallback(Integer orderId){
        OrderDetailsDTO fallbackOrderDetails = new OrderDetailsDTO();
        fallbackOrderDetails.setOrderId(orderId);
        fallbackOrderDetails.setStatus("Order details could not be retrieved. Please try again later.");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackOrderDetails);
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "cancelOrderFallback")
    @DeleteMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam("order_id") int oid){
        try{
            orderService.cancelOrder(oid);
            return ResponseEntity.ok("Order Cancelled Successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not cancelled, check the details entered");
        }
    }
    //fallback
    public ResponseEntity<String> cancelOrderFallback(int oid, Throwable t) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("The order cancellation service is currently unavailable due to a technical issue. " +
                        "Your order with ID " + oid + " could not be cancelled at this time. Please try again later or contact support.");
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "orderedBooksFallback")
    @GetMapping("/orderOfUser/{user_id}")
    public ResponseEntity<List<OrderDetailsDTO>> orderedBooks(@PathVariable("user_id") int uID){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getUserOrders(uID));

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //fallback
    public ResponseEntity<List<OrderDetailsDTO>> orderedBooksFallback(int uID, Throwable t) {
        String message = "We are currently experiencing technical difficulties while fetching your orders. " +
                "Please try again later or contact support if the problem persists.";

        // Optionally, you could return an empty list to indicate that no orders were found
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.emptyList()); // or `null` if appropriate
    }

}
