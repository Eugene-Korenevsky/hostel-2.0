package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.servicies.OrderService;
import com.example.hostel1.servicies.ReserveService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController("adminRestReserveController")
@RequestMapping(path = "admin/reserves", produces = "application/json")
public class ReserveController {
    //@Autowired
    //private OrderRepository orderRepository;
    //@Autowired
    //private ReserveRepository reserveRepository;
    @Autowired
    private TimestampMaker timestampMaker;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private OrderService orderService;

    @PostMapping(value = {"isFree"}, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> makePrice(@RequestParam("dateIn") String dateIn,
                                       @RequestParam("dateOut") String dateOut,
                                       @RequestParam("roomId") long id,
                                       @RequestParam("orderId") long orderId) {
        try {
            StringBuilder result = new StringBuilder();
            Timestamp inDate = Timestamp.valueOf(dateIn.substring(0, 10) + " " + dateIn.substring(11, 19));
            Timestamp outDate = Timestamp.valueOf(dateOut.substring(0, 10) + " " + dateOut.substring(11, 19));
            // if (isRoomFreeInDates.isRoomFree(id, inDate, outDate)) {
            if (reserveService.isFreeInDates(inDate, outDate, id)) {
                Order order = orderService.findById(orderId);
                //orderRepository.findById(orderId).orElse(new Order());
                double price = order.getTotalPrice();
                result.append(price);
            } else {
                result.append("notFree");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReserve(@RequestBody Order order) {
        try {
            Reserve reserve = reserveService.createReserve(order);
            return new ResponseEntity<>(reserve, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        }
    }
}
