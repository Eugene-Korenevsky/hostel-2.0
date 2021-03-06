package com.example.hostel1.repositories;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> readByUser(User user);
}
