package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.User;
import com.example.hostel1.helpers.resources.ReserveFromOrder;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.repositories.spec.ReserveSpec;
import com.example.hostel1.repositories.RoomRepository;
import com.example.hostel1.servicies.ReserveService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.RoomIsExistException;
import com.example.hostel1.servicies.exceptions.RoomIsNotFreeException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Service
public class ReserveServiceImpl extends GenericServiceImpl<Reserve> implements ReserveService {

    private ReserveRepository reserveRepository;

    private OrderRepository orderRepository;
    private ReserveSpec reserveSpec;
    private RoomRepository roomRepository;
    private ReserveFromOrder reserveFromOrder;

    public ReserveServiceImpl(OrderRepository orderRepository, ReserveRepository reserveRepository,
                              ReserveSpec reserveSpec, RoomRepository roomRepository, ReserveFromOrder reserveFromOrder) {
        super(reserveRepository, Reserve.class);
        this.reserveRepository = reserveRepository;
        this.orderRepository = orderRepository;
        this.reserveSpec = reserveSpec;
        this.roomRepository = roomRepository;
        this.reserveFromOrder = reserveFromOrder;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Reserve createReserve(Order order) throws ResourceNotFoundException, ValidationException,
            RoomIsNotFreeException {
        if (order != null) {
            Optional<Order> optional = orderRepository.findById(order.getId());
            if (optional.isPresent()) {
                order = optional.get();
                if (isFreeInDates(order.getDateIn(), order.getDateOut(), order.getRoom().getId())) {
                    Reserve reserve = reserveFromOrder.reserveFromOrder(order);
                    reserve = reserveRepository.save(reserve);
                    orderRepository.delete(order);
                    return reserve;
                }else throw new RoomIsNotFreeException("room is not free");
            } else throw new ResourceNotFoundException("order in not exist");
        } else throw new ValidationException("order must not be null");
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public List<Reserve> findByDatesIntervalAndRoom(
            Timestamp dateIn, Timestamp dateOut, Long roomId) throws ResourceNotFoundException, ValidationException {
        if (dateIn != null || dateOut != null) {
            if (roomId != null) {
                Optional<Room> optionalRoom = roomRepository.findById(roomId);
                Room room;
                if (optionalRoom.isPresent()) room = optionalRoom.get();
                else throw new ResourceNotFoundException("room not found");
                return reserveRepository.findAll(reserveSpec.findByDatesIntervalAndRoom(dateIn, dateOut, room));
            } else throw new ValidationException("room id must not be null");
        } else throw new ValidationException("dates must not be null");
    }

    @Override
    public boolean isFreeInDates(
            Timestamp dateIn, Timestamp dateOut, Long roomId) throws ResourceNotFoundException, ValidationException {
        return findByDatesIntervalAndRoom(dateIn, dateOut, roomId).size() < 1;
    }

    @Override
    public List<Reserve> findAllByUser(User user) throws ValidationException {
        if (user != null) {
            return reserveRepository.findAllByUserId(user.getId());
        } else throw new ValidationException("user must not bu null");
    }
}
