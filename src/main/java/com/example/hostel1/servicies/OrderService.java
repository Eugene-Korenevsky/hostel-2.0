package com.example.hostel1.servicies;


import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.User;
import com.example.hostel1.servicies.exceptions.ValidationException;

import java.util.List;

public interface OrderService extends GenericService<Order> {
    public List<Order> findAllByUser(User user) throws ValidationException;
}
