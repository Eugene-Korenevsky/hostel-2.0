package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.servicies.OrderService;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        super(orderRepository, Order.class);
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllByUser(User user) throws ValidationException {
        if (user != null) {
            return orderRepository.readByUser(user);
        } else throw new ValidationException("user must not be null");
    }
}
