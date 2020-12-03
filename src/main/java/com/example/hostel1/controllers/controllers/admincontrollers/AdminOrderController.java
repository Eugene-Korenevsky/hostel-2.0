package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/order")
public class AdminOrderController {
    @Autowired
    private OrderRepository orderRepository;

    @DeleteMapping(value = {"{id}"})
    public String cancelUserOrder(@PathVariable("id") long orderId) {
        orderRepository.deleteById(orderId);
        return "redirect:/admin/order";
    }

    @GetMapping()
    public String showAdminOrdersList(Map<String, Object> model) {
        Iterable<Order> orders = orderRepository.findAll();
        model.put("orders", orders);
        return "adminOrders";
    }
}
