package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/admin/orders",
        produces = "application/json")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(value = {"{id}"})
    public Order getOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id).orElse(new Order());
        return order;
    }
}
