package com.library.orders.Repository;

import com.library.orders.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findByUserId(int userId);

    List<Order> findAllByUserId(int uID);
}
