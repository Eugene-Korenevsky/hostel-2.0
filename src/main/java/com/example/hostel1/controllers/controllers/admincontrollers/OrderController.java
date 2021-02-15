package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.servicies.OrderService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller("adminOrderController")
@RequestMapping("admin/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @DeleteMapping(value = {"{id}"})
    public String cancelUserOrder(@PathVariable("id") long orderId,Map<String, Object> model) {
        try {
            orderService.deleteById(orderId);
            return "redirect:/admin/order";
        }catch (ResourceNotFoundException e){
            List<Order> orders = orderService.findAll();
            model.put("orders", orders);
            model.put("error","order is not exist");
            return "adminOrders";
        }catch (ValidationException e){
            List<Order> orders = orderService.findAll();
            model.put("orders", orders);
            model.put("error","id must not be null");
            return "adminOrders";
        }

    }

    @GetMapping()
    public String showAdminOrdersList(Map<String, Object> model) {
        List<Order> orders = orderService.findAll();
        model.put("orders", orders);
        return "adminOrders";
    }
}
