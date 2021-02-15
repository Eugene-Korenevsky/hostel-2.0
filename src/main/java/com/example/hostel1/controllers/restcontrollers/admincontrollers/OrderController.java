package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.servicies.OrderService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("adminRestOrderController")
@RequestMapping(path = "/admin/orders",
        produces = "application/json")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = {"{id}"})
    public ResponseEntity<?> getOrder(@PathVariable("id") Long id) {
        try {
            Order order = orderService.findById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        }
    }
}
