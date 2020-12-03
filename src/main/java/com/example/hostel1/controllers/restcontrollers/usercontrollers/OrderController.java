package com.example.hostel1.controllers.restcontrollers.usercontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.User;
import com.example.hostel1.helpers.orderhelpers.IsRoomFreeInDates;
import com.example.hostel1.helpers.orderhelpers.OrderCorrectDate;
import com.example.hostel1.helpers.pricehelpers.TotalPrice;
import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.RoomRepository;
import com.example.hostel1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController("restUserOrderController")
@RequestMapping("order")
public class OrderController {
    @Autowired
    private TimestampMaker timestampMaker;
    @Autowired
    private OrderCorrectDate orderCorrectDate;
    @Autowired
    private IsRoomFreeInDates isRoomFreeInDates;
    @Autowired
    private TotalPrice totalPrice;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;


    @PostMapping(value = {"isFree"}, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public String makePrice(@RequestParam("dateIn") String dateIn,
                            @RequestParam("dateOut") String dateOut,
                            @RequestParam("roomId") long id) {
        StringBuilder result = new StringBuilder();
        Timestamp inDate = timestampMaker.getTimestamp(dateIn, "12:00");
        Timestamp outDate = timestampMaker.getTimestamp(dateOut, "12:00");
        Room room = roomRepository.findById(id).orElse(new Room());
        if (orderCorrectDate.isCorrectDates(dateIn, "12:00", dateOut, "12:00")) {
            if (isRoomFreeInDates.IsRoomFree(id, dateIn, "12:00", dateOut, "12:00")) {
                String price = totalPrice.getTotalPrice(room.getPrice(), inDate, outDate);
                result.append(price);
            } else {
                result.append("notFree");
            }
        } else {
            result.append("wrongDates");
        }
        return result.toString();
    }

    @PostMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Order makeOrder(@RequestParam("dateIn") String dateIn,
                           @RequestParam("dateOut") String dateOut,
                           @RequestParam("roomId") long id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user1 = userRepository.findByEmail(user.getUsername());
        Timestamp inDate = timestampMaker.getTimestamp(dateIn, "12:00");
        Timestamp outDate = timestampMaker.getTimestamp(dateOut, "12:00");
        Room room = roomRepository.findById(id).orElse(new Room());
        // long a = 1;
        // User user1 = userRepository.findById(a).orElse(new User());
        double priceDouble = totalPrice.getTotalPrice(room.getPrice(), dateIn,
                "12:00", dateOut, "12:00");
        Order order = new Order();
        order.setTotalPrice(priceDouble);
        order.setDateIn(inDate);
        order.setDateOut(outDate);
        order.setRoom(room);
        order.setUser(user1);
        order = orderRepository.save(order);
        return order;
    }

    @DeleteMapping(value = {"{id}"})
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id) {
        try {
            orderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
