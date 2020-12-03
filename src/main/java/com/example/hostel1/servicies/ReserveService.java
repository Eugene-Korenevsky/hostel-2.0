package com.example.hostel1.servicies;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;

public interface ReserveService {
    public Reserve createReserve(Order order);
}
