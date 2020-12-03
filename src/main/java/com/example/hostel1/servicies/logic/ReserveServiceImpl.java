package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.servicies.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Reserve createReserve(Order order) {
        Reserve reserve = new Reserve();
        reserve.setDateIn(order.getDateIn());
        reserve.setDateOut(order.getDateOut());
        reserve.setRoom(order.getRoom());
        reserve.setUser(order.getUser());
        reserve.setTotalPrice(order.getTotalPrice());
        reserve = reserveRepository.save(reserve);
        orderRepository.delete(order);
        return reserve;
    }
}
