package com.example.hostel1.servicies;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.User;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.RoomIsExistException;
import com.example.hostel1.servicies.exceptions.RoomIsNotFreeException;
import com.example.hostel1.servicies.exceptions.ValidationException;

import java.sql.Timestamp;
import java.util.List;

public interface ReserveService extends GenericService<Reserve> {
    public Reserve createReserve(Order order) throws ResourceNotFoundException,ValidationException,
            RoomIsNotFreeException;

    public List<Reserve> findByDatesIntervalAndRoom(
            Timestamp dateIn, Timestamp dateOut, Long roomId)throws ResourceNotFoundException, ValidationException;

    public boolean isFreeInDates(
            Timestamp dateIn, Timestamp dateOut, Long roomId)throws ResourceNotFoundException,ValidationException;

    public List<Reserve> findAllByUser(User user)throws ValidationException;
}
